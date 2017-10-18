package co.yiiu.module.notification.repository;

import co.yiiu.module.notification.model.Notification;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

  Page<Notification> findByTargetUser(User targetUser, Pageable pageable);

  Page<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead, Pageable pageable);

  List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead);

  long countByTargetUserAndIsRead(User targetUser, boolean isRead);

  @Modifying
  @Query("update Notification n set n.isRead = true where n.targetUser = ?1")
  void updateByIsRead(User targetUser);

  void deleteByTargetUser(User user);

  void deleteByUser(User user);

  void deleteByTopic(Topic topic);
}
