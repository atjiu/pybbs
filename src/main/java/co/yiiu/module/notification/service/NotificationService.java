package co.yiiu.module.notification.service;

import co.yiiu.module.notification.model.Notification;
import co.yiiu.module.notification.model.NotificationEnum;
import co.yiiu.module.notification.repository.NotificationRepository;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  private NotificationRepository notificationRepository;

  /**
   * 保存通知
   *
   * @param notification
   */
  public void save(Notification notification) {
    notificationRepository.save(notification);
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
   * @param p
   * @param size
   * @param targetUser
   * @param isRead
   * @return
   */
  public Page<Map> findByTargetUserAndIsRead(int p, int size, User targetUser, Boolean isRead) {
    Sort sort = new Sort(Sort.Direction.ASC, "isRead")
        .and(new Sort(Sort.Direction.DESC, "inTime"));
    Pageable pageable = PageRequest.of(p - 1, size, sort);
    if (isRead == null) {
      return notificationRepository.findByTargetUserId(targetUser.getId(), pageable);
    }
    return notificationRepository.findByTargetUserIdAndIsRead(targetUser.getId(), isRead, pageable);
  }

  /**
   * 根据用户查询已读/未读的通知
   *
   * @param targetUser
   * @param isRead
   * @return
   */
  public long countByTargetUserAndIsRead(User targetUser, boolean isRead) {
    return notificationRepository.countByTargetUserIdAndIsRead(targetUser.getId(), isRead);
  }

  /**
   * 根据阅读状态查询通知
   *
   * @param targetUser
   * @param isRead
   * @return
   */
  public List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead) {
    return notificationRepository.findByTargetUserIdAndIsRead(targetUser.getId(), isRead);
  }

  /**
   * 批量更新通知的状态
   *
   * @param targetUser
   */
  public void updateByIsRead(User targetUser) {
    notificationRepository.updateByIsRead(targetUser.getId());
  }

  /**
   * 删除目标用户的通知
   *
   * @param userId
   */
  public void deleteByTargetUser(Integer userId) {
    notificationRepository.deleteByTargetUserId(userId);
  }

  /**
   * 话题被删除了，删除由话题引起的通知信息
   *
   * @param topic
   */
  public void deleteByTopic(Topic topic) {
    notificationRepository.deleteByTopicId(topic.getId());
  }

}
