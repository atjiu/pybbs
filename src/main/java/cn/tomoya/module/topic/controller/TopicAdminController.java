package cn.tomoya.module.topic.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.exception.Result;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
