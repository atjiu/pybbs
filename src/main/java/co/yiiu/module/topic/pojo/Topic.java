package co.yiiu.module.topic.pojo;

import java.util.Date;

public class Topic {
  private Integer id;

  private Integer commentCount;

  private Integer down;

  private Boolean good;

  private Date inTime;

  private Date lastCommentTime;

  private Date modifyTime;

  private String tag;

  private String title;

  private Boolean top;

  private Integer up;

  private Integer userId;

  private Integer view;

  private Double weight;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  public Integer getDown() {
    return down;
  }

  public void setDown(Integer down) {
    this.down = down;
  }

  public Boolean getGood() {
    return good;
  }

  public void setGood(Boolean good) {
    this.good = good;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public Date getLastCommentTime() {
    return lastCommentTime;
  }

  public void setLastCommentTime(Date lastCommentTime) {
    this.lastCommentTime = lastCommentTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag == null ? null : tag.trim();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public Boolean getTop() {
    return top;
  }

  public void setTop(Boolean top) {
    this.top = top;
  }

  public Integer getUp() {
    return up;
  }

  public void setUp(Integer up) {
    this.up = up;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getView() {
    return view;
  }

  public void setView(Integer view) {
    this.view = view;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }
}