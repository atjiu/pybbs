package cn.tomoya.module.topic.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.section.service.SectionService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private ReplyService replyService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private SiteConfig siteConfig;

  @Autowired
  private SectionService sectionService;

  /**
   * 创建话题
   *
   * @return
   */
  @GetMapping("/create")
  public String create(HttpServletResponse response) {
    if (getUser().isBlock()) {
      return renderText(response, "你的帐户已经被禁用了，不能进行此项操作");
    }
    return render("/topic/create");
  }

  /**
   * 保存话题
   *
   * @param title
   * @param content
   * @param model
   * @return
   */
  @PostMapping("/save")
  public String save(String tab, String title, String content, Model model, HttpServletResponse response) {
    if (getUser().isBlock()) {
      return renderText(response, "你的帐户已经被禁用了，不能进行此项操作");
    }
    String errors;
    if (StringUtils.isEmpty(title)) {
      errors = "标题不能为空";
    } else if (sectionService.findByName(tab) == null) {
      errors = "版块不存在";
    } else {
      User user = getUser();
      Topic topic = new Topic();
      topic.setTab(tab);
      topic.setTitle(title);
      topic.setContent(content);
      topic.setInTime(new Date());
      topic.setView(0);
      topic.setUser(user);
      topic.setGood(false);
      topic.setTop(false);
      topic.setEditor(siteConfig.getEditor());
      topic.setLock(false);
      topicService.save(topic);
      return redirect(response, "/topic/" + topic.getId());
    }
    model.addAttribute("errors", errors);
    return render("/topic/create");
  }

  /**
   * 编辑话题
   *
   * @param id
   * @param response
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable int id, HttpServletResponse response, Model model) {
    Topic topic = topicService.findById(id);
    if (topic == null) {
      return renderText(response, "话题不存在");
    } else {
      model.addAttribute("topic", topic);
      return render("/topic/edit");
    }
  }

  /**
   * 更新话题
   *
   * @param title
   * @param content
   * @return
   */
  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id, String tab, String title, String content,
      HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    User user = getUser();
    if (topic.getUser().getId() == user.getId()) {
      if (sectionService.findByName(tab) == null)
        throw new IllegalArgumentException("版块不存在");
      topic.setTab(tab);
      topic.setTitle(title);
      topic.setContent(content);
      topicService.save(topic);
      return redirect(response, "/topic/" + topic.getId());
    } else {
      return renderText(response, "非法操作");
    }
  }

  /**
   * 话题详情
   *
   * @param id
   * @param model
   * @return
   * @throws Exception 
   */
  @GetMapping("/{id}")
  public String detail(@PathVariable(required = true) Integer id, HttpServletResponse response, Model model)
      throws Exception {
    Topic topic = topicService.findById(id);
    if (topic == null)
      throw new Exception("话题不存在");
    // 浏览量+1
    topic.setView(topic.getView() + 1);
    topicService.save(topic);// 更新话题数据
    List<Reply> replies = replyService.findByTopic(topic);
    model.addAttribute("topic", topic);
    model.addAttribute("replies", replies);
    model.addAttribute("user", getUser());
    model.addAttribute("author", topic.getUser());
    model.addAttribute("otherTopics", topicService.findByUser(1, 7, topic.getUser()));
    model.addAttribute("collect", collectService.findByUserAndTopic(getUser(), topic));
    model.addAttribute("collectCount", collectService.countByTopic(topic));
    return render("/topic/detail");
  }

  /**
   * 删除话题
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  public String delete(@PathVariable Integer id, HttpServletResponse response) {
    topicService.deleteById(id);
    return redirect(response, "/");
  }

  /**
   * 加/减精华
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/good")
  public String good(@PathVariable Integer id, HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    topic.setGood(!topic.isGood());
    topicService.save(topic);
    return redirect(response, "/topic/" + id);
  }

  /**
   * 置/不置顶
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/top")
  public String top(@PathVariable Integer id, HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    topic.setTop(!topic.isTop());
    topicService.save(topic);
    return redirect(response, "/topic/" + id);
  }

  /**
   * 锁定/不锁定
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/lock")
  public String lock(@PathVariable Integer id, HttpServletResponse response) {
    Topic topic = topicService.findById(id);
    topic.setLock(!topic.isLock());
    topicService.save(topic);
    return redirect(response, "/topic/" + id);
  }
}
