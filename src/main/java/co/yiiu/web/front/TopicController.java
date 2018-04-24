package co.yiiu.web.front;

import co.yiiu.config.LogEventConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.tag.model.Tag;
import co.yiiu.module.tag.service.TagService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.ReputationPermission;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    User user = getUser();
    Assert.isTrue(user.getReputation() >= ReputationPermission.EDIT_TOPIC.getReputation(), "声望太低，不能进行这项操作");
    Topic topic = topicService.findById(id);
    Assert.notNull(topic, "话题不存在");

    model.addAttribute("topic", topic);
    return "/front/topic/edit";
  }

  @GetMapping("/delete")
  public String delete(Integer id) {
    User user = getUser();
    Assert.isTrue(user.getReputation() >= ReputationPermission.EDIT_TOPIC.getReputation(), "声望太低，不能进行这项操作");
    // delete topic
    topicService.deleteById(id, getUser().getId());
    return redirect("/");
  }

  @GetMapping("/tag/{name}")
  public String tagTopics(@PathVariable String name, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    Tag tag = tagService.findByName(name);
    model.addAttribute("tag", tag);
    model.addAttribute("page", topicService.pageByTagId(pageNo, siteConfig.getPageSize(), tag.getId()));
    return "front/tag/tag";
  }

}
