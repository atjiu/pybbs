package co.yiiu.pybbs.model.vo;

import co.yiiu.pybbs.model.Comment;

import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class CommentsByTopic extends Comment implements Serializable {
  // 话题下面的评论列表单个对象的数据结构

  private String username;
  private String avatar;
  private Integer layer;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getLayer() {
    return layer;
  }

  public void setLayer(Integer layer) {
    this.layer = layer;
  }
}
