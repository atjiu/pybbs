package co.yiiu.module.notification.service;

import co.yiiu.core.base.BaseEntity;
import co.yiiu.module.notification.model.Notification;
import co.yiiu.module.notification.model.NotificationEnum;
import co.yiiu.module.notification.repository.NotificationRepository;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "notifications")
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private UserService userService;

  /**
   * 保存通知
   *
   * @param notification
   */
  @CacheEvict(allEntries = true)
  public void save(Notification notification) {
    notificationRepository.save(notification);
  }

  public void sendNotification(User user, Topic topic, String content) {
    //给话题作者发送通知
    if (user.getId() != topic.getUser().getId()) {
      this.sendNotification(user, topic.getUser(), NotificationEnum.REPLY.name(), topic, content);
    }
    //给At用户发送通知
    List<String> atUsers = BaseEntity.fetchUsers(null, content);
    for (String u : atUsers) {
      u = u.replace("@", "").trim();
      if (!u.equals(user.getUsername())) {
        User _user = userService.findByUsername(u);
        if (_user != null) {
          this.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content);
        }
      }
    }
  }

  /**
   * 发送通知
   *
   * @param user
   * @param targetUser
   * @param action
   * @param topic
   * @param content
   */
  public void sendNotification(User user, User targetUser, String action, Topic topic, String content) {
    new Thread(() -> {
      Notification notification = new Notification();
      notification.setUser(user);
      notification.setTargetUser(targetUser);
      notification.setInTime(new Date());
      notification.setTopic(topic);
      notification.setAction(action);
      notification.setContent(content);
      notification.setRead(false);
      save(notification);
    }).start();
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
  @Cacheable
  public Page<Notification> findByTargetUserAndIsRead(int p, int size, User targetUser, Boolean isRead) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "isRead"), new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    if (isRead == null) {
      return notificationRepository.findByTargetUser(targetUser, pageable);
    }
    return notificationRepository.findByTargetUserAndIsRead(targetUser, isRead, pageable);
  }

  /**
   * 根据用户查询已读/未读的通知
   *
   * @param targetUser
   * @param isRead
   * @return
   */
  @Cacheable
  public long countByTargetUserAndIsRead(User targetUser, boolean isRead) {
    return notificationRepository.countByTargetUserAndIsRead(targetUser, isRead);
  }

  /**
   * 根据阅读状态查询通知
   *
   * @param targetUser
   * @param isRead
   * @return
   */
  @Cacheable
  public List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead) {
    return notificationRepository.findByTargetUserAndIsRead(targetUser, isRead);
  }

  /**
   * 批量更新通知的状态
   *
   * @param targetUser
   */
  @CacheEvict(allEntries = true)
  public void updateByIsRead(User targetUser) {
    notificationRepository.updateByIsRead(targetUser);
  }

  /**
   * 删除用户的通知
   *
   * @param user
   */
  @CacheEvict(allEntries = true)
  public void deleteByUser(User user) {
    notificationRepository.deleteByUser(user);
  }

  /**
   * 删除目标用户的通知
   *
   * @param user
   */
  @CacheEvict(allEntries = true)
  public void deleteByTargetUser(User user) {
    notificationRepository.deleteByTargetUser(user);
  }

  /**
   * 话题被删除了，删除由话题引起的通知信息
   * @param topic
   */
  @CacheEvict(allEntries = true)
  public void deleteByTopic(Topic topic) {
    notificationRepository.deleteByTopic(topic);
  }

}
