package co.yiiu.module.notification.pojo;

import java.util.Date;

public class Notification {
  private Integer id;

  private String action;

  private Date inTime;

  private Boolean isRead;

  private Integer targetUserId;

  private Integer topicId;

  private Integer userId;

  private String content;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action == null ? null : action.trim();
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public Boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(Boolean isRead) {
    this.isRead = isRead;
  }

  public Integer getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(Integer targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Integer getTopicId() {
    return topicId;
  }

  public void setTopicId(Integer topicId) {
    this.topicId = topicId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content == null ? null : content.trim();
  }
}