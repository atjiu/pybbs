package cn.tomoya.module.reply.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.config.base.BaseEntity;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.Result;
import cn.tomoya.module.notification.entity.NotificationEnum;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private ReplyService replyService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private UserService userService;

  /**
   * 保存回复
   *
   * @param topicId
   * @param content
   * @return
   */
  @PostMapping("/save")
  public String save(Integer topicId, String content, HttpServletResponse response) throws Exception {
    User user = getUser();
    if (user.isBlock()) throw new Exception("你的帐户已经被禁用，不能进行此项操作");
    if (user.getScore() < 5) throw new Exception("你的积分不足，不能评论");
    if(StringUtils.isEmpty(content)) throw new Exception("回复内容不能为空");

    if (topicId != null) {
      Topic topic = topicService.findById(topicId);
      if (topic != null) {
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setTopic(topic);
        reply.setInTime(new Date());
        reply.setUp(0);
        reply.setContent(content);
        replyService.save(reply);

        //回复+1
        topic.setReplyCount(topic.getReplyCount() + 1);
        topic.setLastReplyTime(new Date());
        topicService.save(topic);

        //给话题作者发送通知
        if (user.getId() != topic.getUser().getId()) {
          notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.REPLY.name(), topic, content);
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
    Reply _reply = replyService.findById(id);

    if (_reply == null) throw new ApiException("回复不存在");
    if (user.getId() == _reply.getUser().getId()) throw new ApiException("不能给自己的回复点赞");

    Reply reply = replyService.up(user.getId(), _reply);
    return Result.success(reply.getUpDown());
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
    Reply reply = replyService.cancelUp(user.getId(), id);
    if (reply == null) throw new ApiException("回复不存在");
    return Result.success(reply.getUpDown());
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
    Reply _reply = replyService.findById(id);

    if (_reply == null) throw new ApiException("回复不存在");
    if (user.getId() == _reply.getUser().getId()) throw new ApiException("不能给自己的回复点赞");

    Reply reply = replyService.down(user.getId(), _reply);
    return Result.success(reply.getUpDown());
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
    Reply reply = replyService.cancelDown(user.getId(), id);
    if (reply == null) throw new ApiException("回复不存在");
    return Result.success(reply.getUpDown());
  }
}
