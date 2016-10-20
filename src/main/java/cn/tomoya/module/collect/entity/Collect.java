package cn.tomoya.module.collect.entity;

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
@Table(name = "pybbs_collect")
@Entity
@Getter
@Setter
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

}
