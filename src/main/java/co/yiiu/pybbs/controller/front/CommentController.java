package co.yiiu.pybbs.controller.front;

import co.yiiu.pybbs.model.Comment;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.service.CommentService;
import co.yiiu.pybbs.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

  @Autowired
  private CommentService commentService;
  @Autowired
  private TopicService topicService;

  // 编辑评论
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    Comment comment = commentService.selectById(id);
    Topic topic = topicService.selectById(comment.getTopicId());
    model.addAttribute("comment", comment);
    model.addAttribute("topic", topic);
    return render("comment/edit");
  }
}
