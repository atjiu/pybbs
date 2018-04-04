package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/topic")
public class TopicAdminController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private SiteConfig siteConfig;

  /**
   * topic list
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(@RequestParam(defaultValue = "1") Integer p, Model model) {
    model.addAttribute("page", topicService.findAllForAdmin(p, siteConfig.getPageSize()));
    return "admin/topic/list";
  }

  /**
   * 编辑话题
   *
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    Topic topic = topicService.findById(id);
    Assert.notNull(topic, "话题不存在");

    model.addAttribute("topic", topic);
    return "admin/topic/edit";
  }

  /**
   * 更新话题
   *
   * @param title
   * @param content
   * @return
   */
  @PostMapping("/edit")
  @ResponseBody
  public Result update(Integer id, String title, String content) {
    Topic topic = topicService.findById(id);
    topic.setTitle(title);
    topic.setContent(content);
    topic.setModifyTime(new Date());
    topicService.save(topic);

    return Result.success();
  }

  /**
   * delete topic
   *
   * @param id
   * @return
   */
  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    // delete topic
    topicService.deleteById(id, getAdminUser().getId());
    return Result.success();
  }

  /**
   * 加/减精华
   *
   * @param id
   * @return
   */
  @GetMapping("/good")
  @ResponseBody
  public Result good(Integer id) {
    Topic topic = topicService.findById(id);
    topic.setGood(!topic.getGood());
    topicService.save(topic);
    return Result.success();
  }

  /**
   * 置/不置顶
   *
   * @param id
   * @return
   */
  @GetMapping("/top")
  @ResponseBody
  public Result top(Integer id) {
    Topic topic = topicService.findById(id);
    topic.setTop(!topic.getTop());
    topicService.save(topic);
    return Result.success();
  }
}
