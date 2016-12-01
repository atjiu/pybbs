package cn.tomoya.module.reply.entity;

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
@Table(name = "pybbs_reply")
public class Reply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4727003589526236354L;

    @Id
    @GeneratedValue
    private int id;

    //回复的内容
    @Column(columnDefinition = "text", nullable = false)
    private String content;

    //回复时间
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    //点赞数量
    private int up;

    //与话题的关联关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    //与用户的关联关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //对回复点赞的用户id，逗号隔开(英文逗号)
    @Column(columnDefinition = "text")
    private String upIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUpIds() {
        return upIds;
    }

    public void setUpIds(String upIds) {
        this.upIds = upIds;
    }
}
