package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.CollectService;
import co.yiiu.pybbs.service.CommentService;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private CollectService collectService;

  @GetMapping("/{username}")
  public String profile(@PathVariable String username, Model model) {
    // 查询用户个人信息
    User user = userService.selectByUsername(username);
    // 查询用户的话题
    IPage<Map<String, Object>> topics = topicService.selectByUserId(user.getId(), 1, 10);
    // 查询用户参与的评论
    IPage<Map<String, Object>> comments = commentService.selectByUserId(user.getId(), 1, 10);
    // 查询用户收藏的话题数
    Integer collectCount = collectService.countByUserId(user.getId());

    model.addAttribute("user", user);
    model.addAttribute("topics", topics);
    model.addAttribute("comments", comments);
    model.addAttribute("collectCount", collectCount);
    return "front/user/profile";
  }

  @GetMapping("/{username}/topics")
  public String topics(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    // 查询用户个人信息
    User user = userService.selectByUsername(username);
    // 查询用户的话题
    IPage<Map<String, Object>> topics = topicService.selectByUserId(user.getId(), pageNo, null);
    model.addAttribute("user", user);
    model.addAttribute("topics", topics);
    return "front/user/topics";
  }

  @GetMapping("/{username}/comments")
  public String comments(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    // 查询用户个人信息
    User user = userService.selectByUsername(username);
    // 查询用户参与的评论
    IPage<Map<String, Object>> comments = commentService.selectByUserId(user.getId(), pageNo, null);
    model.addAttribute("user", user);
    model.addAttribute("comments", comments);
    return "front/user/comments";
  }

  @GetMapping("/{username}/collects")
  public String collects(@PathVariable String username, @RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    // 查询用户个人信息
    User user = userService.selectByUsername(username);
    // 查询用户参与的评论
    IPage<Map<String, Object>> collects = collectService.selectByUserId(user.getId(), pageNo, null);
    model.addAttribute("user", user);
    model.addAttribute("collects", collects);
    return "front/user/collects";
  }
}
