package co.yiiu.pybbs.service;

import co.yiiu.pybbs.config.service.ElasticSearchService;
import co.yiiu.pybbs.config.service.RedisService;
import co.yiiu.pybbs.mapper.TopicMapper;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.TopicTag;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.Constants;
import co.yiiu.pybbs.util.IpUtil;
import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class TopicService {

  @Autowired
  private TopicMapper topicMapper;
  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private TopicTagService topicTagService;
  @Autowired
  private TagService tagService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private UserService userService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private ElasticSearchService elasticSearchService;
  @Autowired
  private RedisService redisService;

  public MyPage<Map<String, Object>> selectAll(Integer pageNo, String tab) {
    MyPage<Map<String, Object>> page = new MyPage<>(
        pageNo,
        Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString())
    );
    page = topicMapper.selectAll(page, tab);
    selectTags(page, topicTagService, tagService);
    return page;
  }

  public void selectTags(MyPage<Map<String, Object>> page, TopicTagService topicTagService, TagService tagService) {
    page.getRecords().forEach(map -> {
      List<TopicTag> topicTags = topicTagService.selectByTopicId((Integer) map.get("id"));
      List<Integer> tagIds = topicTags.stream().map(TopicTag::getTagId).collect(Collectors.toList());
      List<Tag> tags = tagService.selectByIds(tagIds);
      map.put("tags", tags);
    });
  }

  // 查询话题作者其它的话题
  public List<Topic> selectAuthorOtherTopic(Integer userId, Integer topicId, Integer limit) {
    QueryWrapper<Topic> wrapper = new QueryWrapper<>();
    wrapper
        .eq("user_id", userId)
        .orderByDesc("in_time");
    if (topicId != null) {
      wrapper.lambda().ne(Topic::getId, topicId);
    }
    if (limit != null) wrapper.last("limit " + limit);
    return topicMapper.selectList(wrapper);
  }

  // 查询用户的话题
  public MyPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo,
        pageSize == null ?
            Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString()) : pageSize
    );
    return topicMapper.selectByUserId(iPage, userId);
  }

  // 保存话题
  public Topic insertTopic(String title, String content, String tags, User user, HttpSession session) {
    Topic topic = new Topic();
    topic.setTitle(Jsoup.clean(title, Whitelist.simpleText()));
    topic.setContent(content);
    topic.setInTime(new Date());
    topic.setUserId(user.getId());
    topic.setTop(false);
    topic.setGood(false);
    topic.setView(0);
    topic.setCollectCount(0);
    topic.setCommentCount(0);
    topicMapper.insert(topic);
    // 增加用户积分
    user.setScore(user.getScore() + Integer.parseInt(systemConfigService.selectAllConfig().get("create_topic_score").toString()));
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    // 保存标签
    List<Tag> tagList = tagService.insertTag(Jsoup.clean(tags, Whitelist.none()));
    // 处理标签与话题的关联
    topicTagService.insertTopicTag(topic.getId(), tagList);
    // 索引话题
    indexTopic(String.valueOf(topic.getId()), topic.getTitle(), topic.getContent());
    return topic;
  }

  // 根据id查询话题
  public Topic selectById(Integer id) {
    String topicJson = redisService.getString(Constants.REDIS_TOPIC_KEY + id);
    if (topicJson == null) {
      Topic topic = topicMapper.selectById(id);
      redisService.setString(Constants.REDIS_TOPIC_KEY + id, JsonUtil.objectToJson(topic));
      return topic;
    } else {
      return JsonUtil.jsonToObject(topicJson, Topic.class);
    }
  }

  // 根据title查询话题，防止重复话题
  public Topic selectByTitle(String title) {
    QueryWrapper<Topic> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Topic::getTitle, title);
    return topicMapper.selectOne(wrapper);
  }

  // 处理话题的访问量
  public Topic addViewCount(Topic topic, HttpServletRequest request) {
    String ip = IpUtil.getIpAddr(request);
    ip = ip.replace(":", "_").replace(".", "_");
    if (redisService.isRedisConfig()) {// 如果redis配置了，就采用缓存的形式处理访问量
      String s = redisService.getString(String.format(Constants.REDIS_TOPIC_VIEW_IP_ID_KEY, ip, topic.getId()));
      if (s == null) {
        topic.setView(topic.getView() + 1);
        this.update(topic);
        redisService.setString(String.format(
            Constants.REDIS_TOPIC_VIEW_IP_ID_KEY, ip, topic.getId()),
            String.valueOf(topic.getId()),
            Integer.parseInt(systemConfigService.selectAllConfig().get("topic_view_increase_interval").toString())
        );
      }
    } else {
      topic.setView(topic.getView() + 1);
      this.update(topic);
    }
    return topic;
  }

  // 更新话题
  public void update(Topic topic) {
    topicMapper.updateById(topic);
    // 索引话题
    indexTopic(String.valueOf(topic.getId()), topic.getTitle(), topic.getContent());
    // 缓存到redis里
    redisService.setString(Constants.REDIS_TOPIC_KEY + topic.getId(), JsonUtil.objectToJson(topic));
  }

  // 更新话题
  public Topic updateTopic(Topic topic, String title, String content, String tags) {
    topic.setTitle(Jsoup.clean(title, Whitelist.simpleText()));
    topic.setContent(content);
    topic.setModifyTime(new Date());
    topicMapper.updateById(topic);
    // 旧标签每个topicCount都-1
    tagService.reduceTopicCount(topic.getId());
    // 保存标签
    List<Tag> tagList = tagService.insertTag(Jsoup.clean(tags, Whitelist.none()));
    // 处理标签与话题的关联
    topicTagService.insertTopicTag(topic.getId(), tagList);
    // 索引话题
    indexTopic(String.valueOf(topic.getId()), topic.getTitle(), topic.getContent());
    // 缓存到redis里
    redisService.setString(Constants.REDIS_TOPIC_KEY + topic.getId(), JsonUtil.objectToJson(topic));
    return topic;
  }

  // 删除话题
  public void delete(Topic topic, HttpSession session) {
    Integer id = topic.getId();
    // 删除相关通知
    notificationService.deleteByTopicId(id);
    // 删除相关收藏
    collectService.deleteByTopicId(id);
    // 删除相关的评论
    commentService.deleteByTopicId(id);
    // 将话题对应的标签 topicCount -1
    tagService.reduceTopicCount(id);
    // 删除相应的关联标签
    topicTagService.deleteByTopicId(id);
    // 减去用户积分
    User user = userService.selectById(topic.getUserId());
    user.setScore(user.getScore() - Integer.parseInt(systemConfigService.selectAllConfig().get("delete_topic_score").toString()));
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    // 删除索引
    this.deleteTopicIndex(String.valueOf(topic.getId()));
    // 删除redis缓存
    redisService.delString(Constants.REDIS_TOPIC_KEY + topic.getId());
    // 最后删除话题
    topicMapper.deleteById(id);
  }

  // 根据用户id删除帖子
  public void deleteByUserId(Integer userId) {
    QueryWrapper<Topic> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Topic::getUserId, userId);
    List<Topic> topics = topicMapper.selectList(wrapper);
    topics.forEach(topic -> {
      // 删除redis缓存，这里放在删除索引这里，共用一个话题列表
      redisService.delString(Constants.REDIS_TOPIC_KEY + topic.getId());
      // 删除索引
      this.deleteTopicIndex(String.valueOf(topic.getId()));
    });
    //删除话题
    topicMapper.delete(wrapper);
  }

  // 索引全部话题
  public void indexAllTopic() {
    List<Topic> topics = topicMapper.selectList(null);
    Map<String, Map<String, Object>> sources = topics.stream().collect(Collectors.toMap(key -> String.valueOf(key.getId()), value -> {
      Map<String, Object> map = new HashMap<>();
      map.put("title", value.getTitle());
      map.put("content", value.getContent());
      return map;
    }));
    elasticSearchService.bulkDocument("topic", sources);
  }

  // 索引话题
  public void indexTopic(String id, String title, String content) {
    if (systemConfigService.selectAllConfig().get("search").toString().equals("1")) {
      Map<String, Object> source = new HashMap<>();
      source.put("title", title);
      source.put("content", content);
      elasticSearchService.createDocument("topic", id, source);
    }
  }

  // 删除话题索引
  public void deleteTopicIndex(String id) {
    if (systemConfigService.selectAllConfig().get("search").toString().equals("1")) {
      elasticSearchService.deleteDocument("topic", id);
    }
  }

  // 删除所有话题索引
  public void deleteAllTopicIndex() {
    if (systemConfigService.selectAllConfig().get("search").toString().equals("1")) {
      List<Topic> topics = topicMapper.selectList(null);
      List<Integer> ids = topics.stream().map(Topic::getId).collect(Collectors.toList());
      elasticSearchService.bulkDeleteDocument("topic", ids);
    }
  }
  // ---------------------------- admin ----------------------------

  public MyPage<Map<String, Object>> selectAllForAdmin(Integer pageNo, String startDate, String endDate, String username) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(
        pageNo,
        Integer.parseInt((String) systemConfigService.selectAllConfig().get("page_size"))
    );
    return topicMapper.selectAllForAdmin(iPage, startDate, endDate, username);
  }

  // 查询今天新增的话题数
  public int countToday() {
    return topicMapper.countToday();
  }

  // ---------------------------- api ----------------------------

  public int vote(Topic topic, User user, HttpSession session) {
    String upIds = topic.getUpIds();
    // 将点赞用户id的字符串转成集合
    Set<String> strings = StringUtils.commaDelimitedListToSet(upIds);
    // 把新的点赞用户id添加进集合，这里用set，正好可以去重，如果集合里已经有用户的id了，那么这次动作被视为取消点赞
    Integer userScore = user.getScore();
    if (strings.contains(String.valueOf(user.getId()))) { // 取消点赞行为
      strings.remove(String.valueOf(user.getId()));
      userScore -= Integer.parseInt(systemConfigService.selectAllConfig().get("up_topic_score").toString());
    } else { // 点赞行为
      strings.add(String.valueOf(user.getId()));
      userScore += Integer.parseInt(systemConfigService.selectAllConfig().get("up_topic_score").toString());
    }
    // 再把这些id按逗号隔开组成字符串
    topic.setUpIds(StringUtils.collectionToCommaDelimitedString(strings));
    // 更新评论
    this.update(topic);
    // 增加用户积分
    user.setScore(userScore);
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    return strings.size();
  }

}
