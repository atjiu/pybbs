package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.model.vo.CommentsByTopic;
import co.yiiu.pybbs.service.*;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api/topic")
public class TopicApiController extends BaseApiController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private TagService tagService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private UserService userService;
  @Autowired
  private CollectService collectService;

  // 话题详情
  @GetMapping("/{id}")
  public Result detail(@PathVariable Integer id, HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();
    // 查询话题详情
    Topic topic = topicService.selectById(id);
    // 查询话题关联的标签
    List<Tag> tags = tagService.selectByTopicId(id);
    // 查询话题的评论
    List<CommentsByTopic> comments = commentService.selectByTopicId(id);
    // 查询话题的作者信息
    User topicUser = userService.selectById(topic.getUserId());
    // 查询话题有多少收藏
    List<Collect> collects = collectService.selectByTopicId(id);
    // 如果自己登录了，查询自己是否收藏过这个话题
    User user = getApiUser(false);
    if (user != null) {
      Collect collect = collectService.selectByTopicIdAndUserId(id, user.getId());
      map.put("collect", collect);
    }
    // 话题浏览量+1
    topic = topicService.addViewCount(topic, request);

    map.put("topic", topic);
    map.put("tags", tags);
    map.put("comments", comments);
    map.put("topicUser", topicUser);
    map.put("collects", collects);
    return success(map);
  }

  // 保存话题
  @PostMapping
  public Result create(@RequestBody Map<String, String> body, HttpSession session) {
    User user = getApiUser();
    String title = body.get("title");
    String content = body.get("content");
    String tags = body.get("tags");
    ApiAssert.notEmpty(title, "请输入标题");
    ApiAssert.isNull(topicService.selectByTitle(title), "话题标题重复");
    String[] strings = StringUtils.commaDelimitedListToStringArray(tags);
    Set<String> set = StringUtil.removeEmpty(strings);
    ApiAssert.notTrue(set.isEmpty() || set.size() > 5, "请输入标签且标签最多5个");
    // 保存话题
    // 再次将tag转成逗号隔开的字符串
    tags = StringUtils.collectionToCommaDelimitedString(set);
    Topic topic = topicService.insertTopic(title, content, tags, user, session);
    Map<String, Object> map = new HashMap<>();
    map.put("topic", topic);
    map.put("tags", tagService.selectByTopicId(topic.getId()));
    return success(map);
  }

  // 更新话题
  @PutMapping(value = "/{id}")
  public Result edit(@PathVariable Integer id, @RequestBody Map<String, String> body) {
    User user = getApiUser();
    String title = body.get("title");
    String content = body.get("content");
    String tags = body.get("tags");
    ApiAssert.notEmpty(title, "请输入标题");
    String[] strings = StringUtils.commaDelimitedListToStringArray(tags);
    Set<String> set = StringUtil.removeEmpty(strings);
    ApiAssert.notTrue(set.isEmpty() || set.size() > 5, "请输入标签且标签最多5个");
    // 更新话题
    Topic topic = topicService.selectById(id);
    ApiAssert.isTrue(topic.getUserId().equals(user.getId()), "谁给你的权限修改别人的话题的？");
    // 再次将tag转成逗号隔开的字符串
    tags = StringUtils.collectionToCommaDelimitedString(set);
    topic = topicService.updateTopic(topic, title, content, tags);
    Map<String, Object> map = new HashMap<>();
    map.put("topic", topic);
    map.put("tags", tagService.selectByTopicId(topic.getId()));
    return success(map);
  }

  // 删除话题
  @DeleteMapping("{id}")
  public Result delete(@PathVariable Integer id, HttpSession session) {
    User user = getApiUser();
    Topic topic = topicService.selectById(id);
    ApiAssert.isTrue(topic.getUserId().equals(user.getId()), "谁给你的权限删除别人的话题的？");
    topicService.delete(topic, session);
    return success();
  }

  @GetMapping("/{id}/vote")
  public Result vote(@PathVariable Integer id, HttpSession session) {
    User user = getApiUser();
    Topic topic = topicService.selectById(id);
    ApiAssert.notNull(topic, "这个话题可能已经被删除了");
    ApiAssert.notTrue(topic.getUserId().equals(user.getId()), "给自己话题点赞，脸皮真厚！！");
    int voteCount = topicService.vote(topic, getApiUser(), session);
    return success(voteCount);
  }
}
