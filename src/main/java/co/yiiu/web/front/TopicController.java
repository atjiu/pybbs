package co.yiiu.web.front;

import co.yiiu.config.LogEventConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.EnumUtil;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.tag.model.Tag;
import co.yiiu.module.tag.service.TagService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.model.VoteAction;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.ReputationPermission;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  LogEventConfig logEventConfig;
  @Autowired
  LogService logService;
  @Autowired
  private TagService tagService;

  @GetMapping("/{id}")
  public String detail(@PathVariable Integer id, Model model) {
    Topic topic = topicService.findById(id);
    Assert.notNull(topic, "话题不存在");

    // 浏览量+1
    topic.setView(topic.getView() + 1);
    topicService.save(topic);// 更新话题数据
    model.addAttribute("topic", topic);
    // 查询是否收藏过
    if (getUser() != null) {
      model.addAttribute("collect", collectService.findByUserIdAndTopicId(getUser().getId(), id));
    } else {
      model.addAttribute("collect", null);
    }
    // 查询这个话题被收藏的个数
    model.addAttribute("collectCount", collectService.countByTopicId(id));
    model.addAttribute("topicUser", userService.findById(topic.getUserId()));
    // 查询话题的标签
    List<Tag> tags = tagService.findByTopicId(id);
    model.addAttribute("tags", tags);
    return "/front/topic/detail";
  }

  @GetMapping("/create")
  public String create() {
    User user = getUser();
    Assert.isTrue(!user.getBlock(), "你的帐户已经被禁用了，不能进行此项操作");
    return "front/topic/create";
  }

  @PostMapping("/save")
  @ResponseBody
  public Result save(String title, String content, String tag) {
    User user = getUser();

    ApiAssert.notTrue(user.getBlock(), "你的帐户已经被禁用了，不能进行此项操作");

    ApiAssert.notEmpty(title, "请输入标题");
    ApiAssert.notEmpty(content, "请输入内容");
    ApiAssert.notEmpty(tag, "标签不能为空");
    ApiAssert.notTrue(topicService.findByTitle(title) != null, "话题标题已经存在");

    Topic topic = topicService.createTopic(title, content, tag, user.getId());

    return Result.success(topic);
  }

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    User user = getUser();
    Assert.isTrue(user.getReputation() >= ReputationPermission.EDIT_TOPIC.getReputation(), "声望太低，不能进行这项操作");
    Topic topic = topicService.findById(id);
    Assert.notNull(topic, "话题不存在");

    model.addAttribute("topic", topic);
    return "/front/topic/edit";
  }

  @PostMapping("/edit")
  @ResponseBody
  public Result update(Integer id, String title, String content, String tag) {
    User user = getApiUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.EDIT_TOPIC.getReputation(), "声望太低，不能进行这项操作");

    ApiAssert.notEmpty(title, "请输入标题");
    ApiAssert.notEmpty(content, "请输入内容");
    ApiAssert.notEmpty(tag, "标签不能为空");

    Topic oldTopic = topicService.findById(id);
    ApiAssert.isTrue(oldTopic.getUserId().equals(user.getId()), "不能修改别人的话题");

    Topic topic = oldTopic;
    topic.setTitle(Jsoup.clean(title, Whitelist.none()));
    topic.setContent(Jsoup.clean(content, Whitelist.relaxed()));
    topic.setTag(Jsoup.clean(tag, Whitelist.none()));

    topic = topicService.updateTopic(oldTopic, topic, user.getId());

    return Result.success(topic);
  }

  @GetMapping("/delete")
  public String delete(Integer id) {
    User user = getUser();
    Assert.isTrue(user.getReputation() >= ReputationPermission.EDIT_TOPIC.getReputation(), "声望太低，不能进行这项操作");
    // delete topic
    topicService.deleteById(id, getUser().getId());
    return redirect("/");
  }

  // 给话题投票
  @GetMapping("/{id}/vote")
  @ResponseBody
  public Result vote(@PathVariable Integer id, String action) {
    User user = getApiUser();

    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.VOTE_TOPIC.getReputation(), "声望太低，不能进行这项操作");

    Topic topic = topicService.findById(id);

    ApiAssert.notNull(topic, "话题不存在");
    ApiAssert.notTrue(user.getId().equals(topic.getUserId()), "不能给自己的话题投票");

    ApiAssert.isTrue(EnumUtil.isDefined(VoteAction.values(), action), "参数错误");

    Map<String, Object> map = topicService.vote(user.getId(), topic, action);
    return Result.success(map);
  }

  @GetMapping("/tag/{name}")
  public String tagTopics(@PathVariable String name, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    Tag tag = tagService.findByName(name);
    model.addAttribute("tag", tag);
    model.addAttribute("page", topicService.pageByTagId(pageNo, siteConfig.getPageSize(), tag.getId()));
    return "front/tag/tag";
  }

}
