package co.yiiu.web.front;

import co.yiiu.config.LogEventConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.model.CommentAction;
import co.yiiu.module.comment.service.CommentService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.ReputationPermission;
import co.yiiu.module.user.model.User;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

  /**
   * 保存评论
   *
   * @param topicId
   * @param content
   * @return
   */
  @PostMapping("/save")
  @ResponseBody
  public Result save(Integer topicId, Integer commentId, String content) {
    User user = getApiUser();
    ApiAssert.notTrue(user.getBlock(), "你的帐户已经被禁用，不能进行此项操作");
    ApiAssert.notEmpty(content, "评论内容不能为空");
    ApiAssert.notNull(topicId, "话题ID不存在");

    Topic topic = topicService.findById(topicId);
    ApiAssert.notNull(topic, "回复的话题不存在");

    Comment comment = commentService.createComment(user.getId(), topic, commentId, content);
    return Result.success(comment);
  }

  /**
   * 对评论投票
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/vote")
  @ResponseBody
  public Result vote(@PathVariable Integer id, String action) {
    User user = getApiUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.VOTE_COMMENT.getReputation(), "声望太低，不能进行这项操作");
    Comment comment = commentService.findById(id);

    ApiAssert.notNull(comment, "评论不存在");
    ApiAssert.notTrue(user.getId().equals(comment.getUserId()), "不能给自己的评论投票");

    // 验证参数
    CommentAction commentAction;
    if (action.equalsIgnoreCase("up")) {
      commentAction = CommentAction.UP;
    } else if (action.equalsIgnoreCase("down")) {
      commentAction = CommentAction.DOWN;
    } else {
      return Result.error("参数错误");
    }
    Map<String, Object> map = commentService.vote(user.getId(), comment, commentAction);
    return Result.success(map);
  }

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    User user = getUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.EDIT_COMMENT.getReputation(), "声望太低，不能进行这项操作");

    Comment comment = commentService.findById(id);
    model.addAttribute("topic", topicService.findById(comment.getTopicId()));
    model.addAttribute("comment", comment);
    return "front/comment/edit";
  }

  @PostMapping("/edit")
  @ResponseBody
  public Result edit(Integer id, String content) {
    User user = getApiUser();
    ApiAssert.isTrue(user.getReputation() >= ReputationPermission.EDIT_COMMENT.getReputation(), "声望太低，不能进行这项操作");
    ApiAssert.notEmpty(content, "评论内容不能为空");
    Comment comment = commentService.findById(id);
    Comment oldComment = comment;
    comment.setContent(Jsoup.clean(content, Whitelist.relaxed()));
    commentService.save(comment);
    Topic topic = topicService.findById(comment.getTopicId());
    comment = commentService.update(topic, oldComment, comment, user.getId());
    return Result.success(comment);
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
