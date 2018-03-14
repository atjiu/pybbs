package co.yiiu.web.front;

import co.yiiu.config.ScoreEventConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.base.BaseEntity;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.service.CommentService;
import co.yiiu.module.notification.model.NotificationEnum;
import co.yiiu.module.notification.service.NotificationService;
import co.yiiu.module.score.model.ScoreEventEnum;
import co.yiiu.module.score.model.ScoreLog;
import co.yiiu.module.score.service.ScoreLogService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
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
  private NotificationService notificationService;
  @Autowired
  private UserService userService;

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  FreemarkerUtil freemarkerUtil;
  @Autowired
  ScoreEventConfig scoreEventConfig;

  @Autowired
  ScoreLogService scoreLogService;

  /**
   * 保存评论
   *
   * @param topicId
   * @param content
   * @return
   */
  @PostMapping("/save")
  public String save(Integer topicId, String content, HttpServletResponse response) throws Exception {
    User user = getUser();
    if (user.isBlock()) throw new Exception("你的帐户已经被禁用，不能进行此项操作");
    if (user.getScore() + siteConfig.getCreateCommentScore() < 0) throw new Exception("你的积分不足，不能评论");
    if (StringUtils.isEmpty(content)) throw new Exception("评论内容不能为空");

    if (topicId != null) {
      Topic topic = topicService.findById(topicId);
      if (topic != null) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTopic(topic);
        comment.setInTime(new Date());
        comment.setUp(0);
        comment.setContent(content);
        commentService.save(comment);

        // update score
        user.setScore(user.getScore() + siteConfig.getCreateCommentScore());
        userService.save(user);

        //评论+1
        topic.setCommentCount(topic.getCommentCount() + 1);
        topic.setLastCommentTime(new Date());
        topicService.save(topic);


        //region 记录积分log
        ScoreLog scoreLog = new ScoreLog();

        scoreLog.setInTime(new Date());
        scoreLog.setEvent(ScoreEventEnum.COMMENT_TOPIC.getEvent());
        scoreLog.setChangeScore(siteConfig.getCreateCommentScore());
        scoreLog.setScore(user.getScore());
        scoreLog.setUser(user);

        Map<String, Object> params = Maps.newHashMap();
        params.put("scoreLog", scoreLog);
        params.put("user", user);
        params.put("topic", topic);
        String des = freemarkerUtil.format(scoreEventConfig.getTemplate().get(ScoreEventEnum.COMMENT_TOPIC.getName()), params);
        scoreLog.setEventDescription(des);
        scoreLogService.save(scoreLog);
        //endregion 记录积分log

        //给话题作者发送通知
        if (user.getId() != topic.getUser().getId()) {
          notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.COMMENT.name(), topic, content);
        }
        //给At用户发送通知
        List<String> atUsers = BaseEntity.fetchUsers(null, content);
        for (String u : atUsers) {
          u = u.replace("@", "").trim();
          if (!u.equals(user.getUsername())) {
            User _user = userService.findByUsername(u);
            if (_user != null) {
              notificationService.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content);
            }
          }
        }
        return redirect(response, "/topic/" + topicId);
      }
    }
    return redirect(response, "/");
  }

  /**
   * 点赞
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/up")
  @ResponseBody
  public Result up(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment _comment = commentService.findById(id);

    if (_comment == null) throw new ApiException("评论不存在");
    if (user.getId() == _comment.getUser().getId()) throw new ApiException("不能给自己的评论点赞");

    Comment comment = commentService.up(user.getId(), _comment);
    return Result.success(comment.getUpDown());
  }

  /**
   * 取消点赞
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/cancelUp")
  @ResponseBody
  public Result cancelUp(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment comment = commentService.cancelUp(user.getId(), id);
    if (comment == null) throw new ApiException("评论不存在");
    return Result.success(comment.getUpDown());
  }

  /**
   * 踩
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/down")
  @ResponseBody
  public Result down(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment _comment = commentService.findById(id);

    if (_comment == null) throw new ApiException("评论不存在");
    if (user.getId() == _comment.getUser().getId()) throw new ApiException("不能给自己的评论点赞");

    Comment comment = commentService.down(user.getId(), _comment);
    return Result.success(comment.getUpDown());
  }

  /**
   * 取消踩
   *
   * @param id
   * @return
   * @throws ApiException
   */
  @GetMapping("/{id}/cancelDown")
  @ResponseBody
  public Result cancelDown(@PathVariable Integer id) throws ApiException {
    User user = getUser();
    Comment comment = commentService.cancelDown(user.getId(), id);
    if (comment == null) throw new ApiException("评论不存在");
    return Result.success(comment.getUpDown());
  }
}
