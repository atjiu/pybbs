package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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

  // 保存话题
  @PostMapping("/create")
  public Result create(String title, String content, String tags, HttpSession session) {
    ApiAssert.notEmpty(title, "请输入标题");
    String[] strings = StringUtils.commaDelimitedListToStringArray(tags);
    Set<String> set = StringUtil.removeEmpty(strings);
    ApiAssert.notTrue(set.size() > 5 || set.size() == 0, "请输入标签且标签最多5个");
    //保存话题
    // 再次将tag转成逗号隔开的字符串
    tags = StringUtils.collectionToCommaDelimitedString(set);
    Topic topic = topicService.insertTopic(title, content, tags, getUser(), session);
    return success(topic);
  }

  // 更新话题
  @PostMapping("/edit")
  public Result edit(Integer id, String title, String content, String tags) {
    ApiAssert.notEmpty(title, "请输入标题");
    String[] strings = StringUtils.commaDelimitedListToStringArray(tags);
    Set<String> set = StringUtil.removeEmpty(strings);
    ApiAssert.notTrue(set.size() > 5 || set.size() == 0, "请输入标签且标签最多5个");
    // 更新话题
    Topic topic = topicService.selectById(id);
    ApiAssert.isTrue(topic.getUserId().equals(getUser().getId()), "谁给你的权限修改别人的话题的？");
    // 再次将tag转成逗号隔开的字符串
    tags = StringUtils.collectionToCommaDelimitedString(set);
    topic = topicService.updateTopic(topic, title, content, tags);
    return success(topic);
  }

  // 删除话题
  @GetMapping("/delete")
  public Result delete(Integer id, HttpSession session) {
    Topic topic = topicService.selectById(id);
    ApiAssert.isTrue(topic.getUserId().equals(getUser().getId()), "谁给你的权限删除别人的话题的？");
    topicService.delete(topic, session);
    return success();
  }

  @GetMapping("/vote")
  public Result vote(Integer id, HttpSession session) {
    Topic topic = topicService.selectById(id);
    ApiAssert.notNull(topic, "这个话题可能已经被删除了");
    ApiAssert.notTrue(topic.getUserId().equals(getUser().getId()), "给自己话题点赞，脸皮真厚！！");
    int voteCount = topicService.vote(topic, getUser(), session);
    return success(voteCount);
  }
}
