package cn.tomoya.module.topic.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.exception.Result;
import cn.tomoya.module.label.service.LabelService;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.section.service.SectionService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/topic")
public class TopicAdminController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private SectionService sectionService;
  @Autowired
  private LabelService labelService;

  /**
   * topic list
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    model.addAttribute("p", p);
    return render("/admin/topic/list");
  }

  /**
   * 编辑话题
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable int id, Model model) throws Exception {
    Topic topic = topicService.findById(id);
    if (topic == null) throw new Exception("话题不存在");

    model.addAttribute("topic", topic);
    return render("/admin/topic/edit");
  }

  /**
   * 更新话题
   *
   * @param title
   * @param content
   * @return
   */
  @PostMapping("/{id}/edit")
  public String update(@PathVariable Integer id, String tab, String oldLabels, String labels, String title, String content,
                       HttpServletResponse response) throws Exception {
    Topic topic = topicService.findById(id);

    if (sectionService.findByName(tab) == null)
      throw new Exception("版块不存在");

    // deal label
    labelService.dealEditTopicOldLabels(oldLabels);
    topic.setLabelId(labelService.dealLabels(labels));

    topic.setTab(tab);
    topic.setTitle(title);
    topic.setContent(content);
    topic.setModifyTime(new Date());
    topicService.save(topic);
    return redirect(response, "/topic/" + topic.getId());
  }

  /**
   * delete topic
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
    String beforeUrl = request.getHeader("referer");
    // delete topic
    topicService.deleteById(id);
    return redirect(response, beforeUrl == null ? "/admin/topic/list" : beforeUrl);
  }

  /**
   * 加/减精华
   *
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
   *
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
   *
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
