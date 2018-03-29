package co.yiiu.module.notification.repository;

import co.yiiu.module.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

  @Query(value = "select n as notification, t as topic, u as user from Notification n, Topic t, User u where t.id = n.topicId and n.userId = u.id and n.targetUserId = ?1",
      countQuery = "select count(1) from Notification n, Topic t, User u where t.id = n.topicId and n.userId = u.id and n.targetUserId = ?1")
  Page<Map> findByTargetUserId(Integer targetUserId, Pageable pageable);

  @Query(value = "select n as notification, t as topic, u as user from Notification n, Topic t, User u where t.id = n.topicId and n.userId = u.id and n.targetUserId = ?1 and n.isRead = ?2",
      countQuery = "select count(1) from Notification n, Topic t, User u where t.id = n.topicId and n.userId = u.id and n.targetUserId = ?1 and n.isRead = ?2")
  Page<Map> findByTargetUserIdAndIsRead(Integer targetUserId, boolean isRead, Pageable pageable);

  List<Notification> findByTargetUserIdAndIsRead(Integer targetUserId, boolean isRead);

  long countByTargetUserIdAndIsRead(Integer targetUserId, boolean isRead);

  @Modifying
  @Query("update Notification n set n.isRead = true where n.targetUserId = ?1")
  void updateByIsRead(Integer targetUserId);

  void deleteByTargetUserId(Integer userId);

  void deleteByUserId(Integer userId);

  void deleteByTopicId(Integer topicId);
}
