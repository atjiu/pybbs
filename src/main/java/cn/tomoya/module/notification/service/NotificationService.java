package cn.tomoya.module.notification.service;

import cn.tomoya.module.notification.dao.NotificationDao;
import cn.tomoya.module.notification.entity.Notification;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
 * http://tomoya.cn
 */
@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public void save(Notification notification) {
        notificationDao.save(notification);
    }

    /**
     * send notification
     *
     * @param user
     * @param targetUser
     * @param action
     * @param topic
     * @param content
     */
    public void sendNotification(User user, User targetUser, String action, Topic topic, String content, String editor) {
        new Thread(() -> {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTargetUser(targetUser);
            notification.setInTime(new Date());
            notification.setTopic(topic);
            notification.setAction(action);
            notification.setContent(content);
            notification.setRead(false);
            notification.setEditor(editor);
            save(notification);
        }).start();
    }

    /**
     * query notifications by user
     *
     * @param p
     * @param size
     * @param targetUser
     * @param isRead
     * @return
     */
    public Page<Notification> findByTargetUserAndIsRead(int p, int size, User targetUser, Boolean isRead) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "isRead"), new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        if (isRead == null) {
            return notificationDao.findByTargetUser(targetUser, pageable);
        }
        return notificationDao.findByTargetUserAndIsRead(targetUser, isRead, pageable);
    }

    /**
     * query the total of notification by user and the notification read status
     *
     * @param targetUser
     * @param isRead
     * @return
     */
    public long countByTargetUserAndIsRead(User targetUser, boolean isRead) {
        return notificationDao.countByTargetUserAndIsRead(targetUser, isRead);
    }

    /**
     * query notification by user and notification read status
     *
     * @param targetUser
     * @param isRead
     * @return
     */
    public List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead) {
        return notificationDao.findByTargetUserAndIsRead(targetUser, isRead);
    }

    /**
     * the status of the bulk update notification
     *
     * @param targetUser
     */
    public void updateByIsRead(User targetUser) {
        notificationDao.updateByIsRead(targetUser);
    }

    /**
     * delete user notification
     * @param user
     */
    public void deleteByUser(User user) {
        notificationDao.deleteByUser(user);
    }

    /**
     * delete target user nofitication
     * @param user
     */
    public void deleteByTargetUser(User user) {
        notificationDao.deleteByTargetUser(user);
    }
}
