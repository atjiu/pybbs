package co.yiiu.module.notification.service;

import co.yiiu.core.bean.Page;
import co.yiiu.module.notification.mapper.NotificationMapper;
import co.yiiu.module.notification.pojo.Notification;
import co.yiiu.module.notification.pojo.NotificationEnum;
import co.yiiu.module.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class NotificationService {

  @Autowired
  private NotificationMapper notificationMapper;

  /**
   * 保存通知
   *
   * @param notification
   */
  public void save(Notification notification) {
    notificationMapper.insertSelective(notification);
  }

  /**
   * 发送通知
   *
   * @param userId
   * @param targetUserId
   * @param action
   * @param topicId
   * @param content
   */
  public void sendNotification(Integer userId, Integer targetUserId, NotificationEnum action, Integer topicId, String content) {
    Notification notification = new Notification();
    notification.setUserId(userId);
    notification.setTargetUserId(targetUserId);
    notification.setInTime(new Date());
    notification.setTopicId(topicId);
    notification.setAction(action.name());
    notification.setContent(content);
    notification.setIsRead(false);
    save(notification);
  }

  /**
   * 根据用户查询通知
   *
   * @param targetUser
   * @param isRead
   * @return
   */
  public Page<Map> findByTargetUserAndIsRead(Integer pageNo, Integer pageSize, User targetUser, Boolean isRead) {
    List<Map> list = notificationMapper.findByTargetUserId(targetUser.getId(), isRead, (pageNo - 1) * pageSize, pageSize, "n.is_read asc, n.id desc");
    int count = notificationMapper.countByTargetUserId(targetUser.getId(), isRead);
    return new Page<>(pageNo, pageSize, count, list);
  }

  /**
   * 根据用户查询已读/未读的通知
   *
   * @param targetUser
   * @param isRead
   * @return
   */
  public long countByTargetUserAndIsRead(User targetUser, boolean isRead) {
    return notificationMapper.countByTargetUserId(targetUser.getId(), isRead);
  }

  /**
   * 批量更新通知的状态
   *
   * @param targetUser
   */
  public void updateByIsRead(User targetUser) {
    notificationMapper.updateByIsRead(targetUser.getId());
  }

  /**
   * 删除目标用户的通知
   *
   * @param targetUserId
   */
  public void deleteByTargetUser(Integer targetUserId) {
    notificationMapper.deleteNotification(targetUserId, null, null);
  }

  /**
   * 话题被删除了，删除由话题引起的通知信息
   *
   * @param topicId
   */
  public void deleteByTopic(Integer topicId) {
    notificationMapper.deleteNotification(null, null, topicId);
  }

}
