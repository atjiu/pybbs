package cn.tomoya.module.notification.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_notification")
@Getter
@Setter
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    //通知是否已读
    @Column(name = "is_read")
    private boolean isRead;

    //发起通知用户
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //要通知用户
    @ManyToOne
    @JoinColumn(nullable = false, name = "target_user_id")
    private User targetUser;

    @Column(name = "in_time")
    private Date inTime;

    //通知动作
    private String action;

    //关联的话题
    @ManyToOne
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    //通知内容（冗余字段）
    @Column(columnDefinition = "text")
    private String content;

}
