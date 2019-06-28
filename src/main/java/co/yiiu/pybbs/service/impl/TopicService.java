package co.yiiu.pybbs.service.impl;

import co.yiiu.pybbs.mapper.TopicMapper;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.plugin.ElasticSearchService;
import co.yiiu.pybbs.service.*;
import co.yiiu.pybbs.util.MyPage;
import co.yiiu.pybbs.util.SensitiveWordUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
public class TopicService implements ITopicService {

  @Autowired
  private TopicMapper topicMapper;
  @Autowired
  private ISystemConfigService systemConfigService;
  @Autowired
  private ITopicTagService topicTagService;
  @Autowired
  private ITagService tagService;
  @Autowired
  @Lazy
  private ICollectService collectService;
  @Autowired
  @Lazy
  private ICommentService commentService;
  @Autowired
  @Lazy
  private IUserService userService;
  @Autowired
  private INotificationService notificationService;
  @Autowired
  private ElasticSearchService elasticSearchService;

  @Override
  public MyPage<Map<String, Object>> search(Integer pageNo, Integer pageSize, String keyword) {
    if (pageSize == null)
      pageSize = Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString());
    MyPage<Map<String, Object>> page = new MyPage<>(pageNo, pageSize);
    return topicMapper.search(page, keyword);
  }

  @Override
  public MyPage<Map<String, Object>> selectAll(Integer pageNo, String tab) {
    MyPage<Map<String, Object>> page = new MyPage<>(pageNo, Integer.parseInt(systemConfigService.selectAllConfig()
        .get("page_size").toString()));
    page = topicMapper.selectAll(page, tab);
    // 查询话题的标签
    tagService.selectTagsByTopicId(page);
    return page;
  }

  // 查询话题作者其它的话题
  @Override
  public List<Topic> selectAuthorOtherTopic(Integer userId, Integer topicId, Integer limit) {
    QueryWrapper<Topic> wrapper = new QueryWrapper<>();
    wrapper.eq("user_id", userId).orderByDesc("in_time");
    if (topicId != null) {
      wrapper.lambda().ne(Topic::getId, topicId);
    }
    if (limit != null) wrapper.last("limit " + limit);
    return topicMapper.selectList(wrapper);
  }

  // 查询用户的话题
  @Override
  public MyPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo, pageSize == null ? Integer.parseInt(systemConfigService
        .selectAllConfig().get("page_size").toString()) : pageSize);
    MyPage<Map<String, Object>> page = topicMapper.selectByUserId(iPage, userId);
    for (Map<String, Object> map : page.getRecords()) {
      Object content = map.get("content");
      map.put("content", StringUtils.isEmpty(content) ? null : SensitiveWordUtil.replaceSensitiveWord(content
          .toString(), "*", SensitiveWordUtil.MinMatchType));
    }
    return page;
  }

  // 保存话题
  @Override
  public Topic insert(String title, String content, String tags, User user, HttpSession session) {
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
    user.setScore(user.getScore() + Integer.parseInt(systemConfigService.selectAllConfig().get("create_topic_score")
        .toString()));
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    if (!StringUtils.isEmpty(tags)) {
      // 保存标签
      List<Tag> tagList = tagService.insertTag(Jsoup.clean(tags, Whitelist.none()));
      // 处理标签与话题的关联
      topicTagService.insertTopicTag(topic.getId(), tagList);
    }
    // 索引话题
    indexTopic(String.valueOf(topic.getId()), topic.getTitle(), topic.getContent());
    return topic;
  }

  // 根据id查询话题
  @Override
  public Topic selectById(Integer id) {
    return topicMapper.selectById(id);
  }

  // 根据title查询话题，防止重复话题
  @Override
  public Topic selectByTitle(String title) {
    QueryWrapper<Topic> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(Topic::getTitle, title);
    return topicMapper.selectOne(wrapper);
  }

  // 处理话题的访问量
  @Override
  public Topic updateViewCount(Topic topic, String ip) {
    topic.setView(topic.getView() + 1);
    this.update(topic, null);
    return topic;
  }

  // 更新话题
  @Override
  public void update(Topic topic, String tags) {
    topicMapper.updateById(topic);
    // 处理标签
    if (!StringUtils.isEmpty(tags)) {
      // 旧标签每个topicCount都-1
      tagService.reduceTopicCount(topic.getId());
      if (!StringUtils.isEmpty(tags)) {
        // 保存标签
        List<Tag> tagList = tagService.insertTag(Jsoup.clean(tags, Whitelist.none()));
        // 处理标签与话题的关联
        topicTagService.insertTopicTag(topic.getId(), tagList);
      }
    }
    // 索引话题
    indexTopic(String.valueOf(topic.getId()), topic.getTitle(), topic.getContent());
  }

  // 删除话题
  @Override
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
    user.setScore(user.getScore() - Integer.parseInt(systemConfigService.selectAllConfig().get("delete_topic_score")
        .toString()));
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    // 删除索引
    this.deleteTopicIndex(String.valueOf(topic.getId()));
    // 最后删除话题
    topicMapper.deleteById(id);
  }

  // 根据用户id删除帖子
  @Override
  public void deleteByUserId(Integer userId) {
    QueryWrapper<Topic> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(Topic::getUserId, userId);
    List<Topic> topics = topicMapper.selectList(wrapper);
    topics.forEach(topic -> {
      // 删除索引
      this.deleteTopicIndex(String.valueOf(topic.getId()));
    });
    //删除话题
    topicMapper.delete(wrapper);
  }

  // 索引全部话题
  @Override
  public void indexAllTopic() {
    List<Topic> topics = topicMapper.selectList(null);
    Map<String, Map<String, Object>> sources = topics.stream().collect(Collectors.toMap(key -> String.valueOf(key
        .getId()), value -> {
      Map<String, Object> map = new HashMap<>();
      map.put("title", value.getTitle());
      map.put("content", value.getContent());
      return map;
    }));
    elasticSearchService.bulkDocument("topic", sources);
  }

  // 索引话题
  @Override
  public void indexTopic(String id, String title, String content) {
    if (systemConfigService.selectAllConfig().get("search").toString().equals("1")) {
      Map<String, Object> source = new HashMap<>();
      source.put("title", title);
      source.put("content", content);
      elasticSearchService.createDocument("topic", id, source);
    }
  }

  // 删除话题索引
  @Override
  public void deleteTopicIndex(String id) {
    if (systemConfigService.selectAllConfig().get("search").toString().equals("1")) {
      elasticSearchService.deleteDocument("topic", id);
    }
  }

  // 删除所有话题索引
  @Override
  public void deleteAllTopicIndex() {
    if (systemConfigService.selectAllConfig().get("search").toString().equals("1")) {
      List<Topic> topics = topicMapper.selectList(null);
      List<Integer> ids = topics.stream().map(Topic::getId).collect(Collectors.toList());
      elasticSearchService.bulkDeleteDocument("topic", ids);
    }
  }
  // ---------------------------- admin ----------------------------

  @Override
  public MyPage<Map<String, Object>> selectAllForAdmin(Integer pageNo, String startDate, String endDate, String
      username) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo, Integer.parseInt((String) systemConfigService
        .selectAllConfig().get("page_size")));
    return topicMapper.selectAllForAdmin(iPage, startDate, endDate, username);
  }

  // 查询今天新增的话题数
  @Override
  public int countToday() {
    return topicMapper.countToday();
  }

  // ---------------------------- api ----------------------------

  @Override
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
    this.update(topic, null);
    // 增加用户积分
    user.setScore(userScore);
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    return strings.size();
  }

}
