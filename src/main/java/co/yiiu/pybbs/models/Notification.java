package co.yiiu.pybbs.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/9/14
 */
public class Notification implements Serializable {

  @Id
  private String id;
  private String topicId;
  private String userId;
  // 通知对象ID
  private String targetUserId;
  // 动作: REPLY, COMMENT, COLLECT
  private String action;
  private Date inTime;
  // 是否已读
  private Boolean read;

  @Transient
  private Topic topic;
  @Transient
  private User user;

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

  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    this.read = read;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTopicId() {
    return topicId;
  }

  public void setTopicId(String topicId) {
    this.topicId = topicId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }
}
