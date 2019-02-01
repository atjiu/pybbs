package co.yiiu.pybbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@TableName("oauth_user")
public class OAuthUser implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;
  // oauth帐号的id
  private Integer oauthId;
  // 帐号类型，GITHUB, QQ, WECHAT, WEIBO 等
  @TableField("`type`")
  private String type;
  // oauth帐号的登录名
  private String login;
  @JsonIgnore
  private String accessToken;
  private Date inTime;
  // 个人简介
  private String bio;
  private String email;
  // 本地用户的id
  private Integer userId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOauthId() {
    return oauthId;
  }

  public void setOauthId(Integer oauthId) {
    this.oauthId = oauthId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}
