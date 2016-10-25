package cn.tomoya.module.collect.entity;

import cn.tomoya.common.BaseEntity;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Table(name = "pybbs_collect")
@Entity
public class Collect extends BaseEntity {

    @Id
    @GeneratedValue
    private int id;

    //与话题的关联关系
    @ManyToOne
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    //与用户的关联关系
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "in_time")
    private Date inTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
}
