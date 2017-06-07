package cn.tomoya.module.notification.service;

import cn.tomoya.config.base.BaseEntity;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.notification.dao.NotificationDao;
import cn.tomoya.module.notification.entity.Notification;
import cn.tomoya.module.notification.entity.NotificationEnum;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
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
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private UserService userService;

    /**
     * 保存通知
     *
     * @param notification
     */
    public void save(Notification notification) {
        notificationDao.save(notification);
    }

    public void sendNotification(User user, Topic topic, String content, Reply reply) {
        //给话题作者发送通知
        if (user.getId() != topic.getUser().getId()) {
            this.sendNotification(user, topic.getUser(), NotificationEnum.REPLY.name(), topic, content, reply.getEditor());
        }
        //给At用户发送通知
        String pattern = null;
        if(siteConfig.getEditor().equals("wangeditor")) pattern = "\">[^\\s]+</a>?";
        List<String> atUsers = BaseEntity.fetchUsers(pattern, content);
        for (String u : atUsers) {
            if(siteConfig.getEditor().equals("markdown")) {
                u = u.replace("@", "").trim();
            } else if(siteConfig.getEditor().equals("wangeditor")) {
                u = u.replace("\">@", "").replace("</a>", "").trim();
            }
            if (!u.equals(user.getUsername())) {
                User _user = userService.findByUsername(u);
                if (_user != null) {
                    this.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content, reply.getEditor());
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
     * 根据用户查询通知
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
     * 根据用户查询已读/未读的通知
     *
     * @param targetUser
     * @param isRead
     * @return
     */
    public long countByTargetUserAndIsRead(User targetUser, boolean isRead) {
        return notificationDao.countByTargetUserAndIsRead(targetUser, isRead);
    }

    /**
     * 根据阅读状态查询通知
     *
     * @param targetUser
     * @param isRead
     * @return
     */
    public List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead) {
        return notificationDao.findByTargetUserAndIsRead(targetUser, isRead);
    }

    /**
     * 批量更新通知的状态
     *
     * @param targetUser
     */
    public void updateByIsRead(User targetUser) {
        notificationDao.updateByIsRead(targetUser);
    }

    /**
     * 删除用户的通知
     * @param user
     */
    public void deleteByUser(User user) {
        notificationDao.deleteByUser(user);
    }

    /**
     * 删除目标用户的通知
     * @param user
     */
    public void deleteByTargetUser(User user) {
        notificationDao.deleteByTargetUser(user);
    }

    public void deleteByTopic(Topic topic) {
        notificationDao.deleteByTopic(topic);
    }

}
