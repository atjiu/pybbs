package co.yiiu.web.front;

import co.yiiu.config.LogEventConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.service.CommentService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.ReputationPermission;
import co.yiiu.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  LogEventConfig logEventConfig;
  @Autowired
  LogService logService;

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    User user = getUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.EDIT_COMMENT.getReputation(), "声望太低，不能进行这项操作");

    Comment comment = commentService.findById(id);
    model.addAttribute("topic", topicService.findById(comment.getTopicId()));
    model.addAttribute("comment", comment);
    return "front/comment/edit";
  }

  @GetMapping("/delete")
  public String delete(Integer id) {
    User user = getUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.DELETE_COMMENT.getReputation(), "声望太低，不能进行这项操作");

    Comment comment = commentService.findById(id);
    Assert.notNull(comment, "评论不存在");
    Integer topicId = comment.getTopicId();
    commentService.delete(id, getUser().getId());
    return redirect("/topic/" + topicId);
  }

}
