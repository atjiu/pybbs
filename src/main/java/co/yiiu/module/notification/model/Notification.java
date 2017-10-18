package co.yiiu.module.notification.model;

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
@Entity
@Table(name = "yiiu_notification")
public class Notification implements Serializable {

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
  @JsonFormat(pattern = Constants.DATETIME_FORMAT)
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
}
