package co.yiiu.module.collect.model;

import co.yiiu.core.util.Constants;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Table(name = "yiiu_collect")
@Entity
public class Collect implements Serializable {

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
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
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
