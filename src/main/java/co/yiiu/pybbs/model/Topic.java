package co.yiiu.pybbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class Topic implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  private Integer id;
  private String title;
  private String content;
  private Date inTime;
  private Date modifyTime;
  private Integer userId;
  // 评论数
  private Integer commentCount;
  // 收藏数
  private Integer collectCount;
  // 浏览数
  private Integer view;
  // 置顶
  private Boolean top;
  // 加精
  private Boolean good;
  // 点赞用户的id英文,隔开的，要计算被多少人点赞过，可以通过英文,分隔这个字符串计算数量
  private String upIds;

  public String getUpIds() {
    return upIds;
  }

  public void setUpIds(String upIds) {
    this.upIds = upIds;
  }

  public Integer getCollectCount() {
    return collectCount;
  }

  public void setCollectCount(Integer collectCount) {
    this.collectCount = collectCount;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  public Integer getView() {
    return view;
  }

  public void setView(Integer view) {
    this.view = view;
  }

  public Boolean getTop() {
    return top;
  }

  public void setTop(Boolean top) {
    this.top = top;
  }

  public Boolean getGood() {
    return good;
  }

  public void setGood(Boolean good) {
    this.good = good;
  }
}
