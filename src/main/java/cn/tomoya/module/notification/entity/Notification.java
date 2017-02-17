package cn.tomoya.module.notification.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Entity
@Table(name = "pybbs_notification")
public class Notification extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -619423593415705785L;

    @Id
    @GeneratedValue
    private int id;

    //Whether the notification has been read
    @Column(name = "is_read")
    private boolean isRead;

    //Initiate notification user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //Be notified to the user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "target_user_id")
    private User targetUser;

    @Column(name = "in_time")
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    //notification action
    private String action;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    //notification content（Redundant field）
    @Column(columnDefinition = "text")
    private String content;

    //editor name
    private String editor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
