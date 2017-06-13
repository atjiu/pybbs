package cn.tomoya.module.topic.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.section.service.SectionService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
  private CollectService collectService;
  @Autowired
  private SectionService sectionService;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;

  /**
   * 创建话题
   *
   * @return
   */
  @GetMapping("/create")
  public String create() throws Exception {
    if (getUser().isBlock()) throw new Exception("你的帐户已经被禁用了，不能进行此项操作");

    String now = DateUtil.formatDate(new Date());
    Date date1 = DateUtil.string2Date(now + " 00:00:00", DateUtil.FORMAT_DATETIME);
    Date date2 = DateUtil.string2Date(now + " 23:59:59", DateUtil.FORMAT_DATETIME);
    if (siteConfig.getMaxCreateTopic() < topicService.countByInTimeBetween(date1, date2))
      throw new Exception("你今天发布的话题超过系统设置的最大值，请明天再发");

    return render("/front/topic/create");
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
  public String save(String tab, String title, String content, Model model, HttpServletResponse response) throws Exception {
    if (getUser().isBlock()) throw new Exception("你的帐户已经被禁用了，不能进行此项操作");

    String now = DateUtil.formatDate(new Date());
    Date date1 = DateUtil.string2Date(now + " 00:00:00", DateUtil.FORMAT_DATETIME);
    Date date2 = DateUtil.string2Date(now + " 23:59:59", DateUtil.FORMAT_DATETIME);
    if (siteConfig.getMaxCreateTopic() < topicService.countByInTimeBetween(date1, date2))
      throw new Exception("你今天发布的话题超过系统设置的最大值，请明天再发");

    String errors;
    if (StringUtils.isEmpty(title)) {
      errors = "标题不能为空";
    } else if (sectionService.findByName(tab) == null) {
      errors = "版块不存在";
    } else {
      if(topicService.findByTitle(title) != null) throw new Exception("话题标题已经存在");

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
      topic.setLock(false);
      topicService.save(topic);

      // plus 5 score
      user.setScore(user.getScore() + 5);
      userService.save(user);
      return redirect(response, "/topic/" + topic.getId());
    }
    model.addAttribute("errors", errors);
    return render("/front/topic/create");
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
    return render("/front/topic/edit");
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
                       HttpServletResponse response) throws Exception {
    Topic topic = topicService.findById(id);
    User user = getUser();
    
    if (topic.getUser().getId() != user.getId())
      throw new Exception("非法操作");

    if (sectionService.findByName(tab) == null)
      throw new Exception("版块不存在");

    topic.setTab(tab);
    topic.setTitle(title);
    topic.setContent(content);
    topicService.save(topic);
    return redirect(response, "/topic/" + topic.getId());
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
  public String detail(@PathVariable Integer id, Model model)
      throws Exception {
    Topic topic = topicService.findById(id);
    if (topic == null)
      throw new Exception("话题不存在");
    // 浏览量+1
    topic.setView(topic.getView() + 1);
    topicService.save(topic);// 更新话题数据
    model.addAttribute("topic", topic);
    // 查询是否收藏过
    model.addAttribute("collect", collectService.findByUserAndTopic(getUser(), topic));
    // 查询这个话题被收藏的个数
    model.addAttribute("collectCount", collectService.countByTopic(topic));
    return render("/front/topic/detail");
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

    // reduce 5 score
    User user = getUser();
    user.setScore(user.getScore() - 5);
    userService.save(user);

    return redirect(response, "/");
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
