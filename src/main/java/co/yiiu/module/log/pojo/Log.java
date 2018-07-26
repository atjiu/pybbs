package co.yiiu.module.log.pojo;

import java.util.Date;

public class Log {
  private Integer id;

  private String event;

  private Date inTime;

  private String target;

  private Integer targetId;

  private Integer userId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event == null ? null : event.trim();
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target == null ? null : target.trim();
  }

  public Integer getTargetId() {
    return targetId;
  }

  public void setTargetId(Integer targetId) {
    this.targetId = targetId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}