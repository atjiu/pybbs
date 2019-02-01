package co.yiiu.pybbs.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class User implements Serializable {

  private static final long serialVersionUID = -5051120337175047163L;

  @TableId(type = IdType.AUTO)
  private Integer id;
  private String username;
  private String telegramName;
  private String avatar;
  @JsonIgnore
  private String password;
  private String email;
  // 个人网站
  private String website;
  // 个人简介
  private String bio;
  private Integer score;
  private Date inTime;
  @JsonIgnore
  private String token;
  // 有消息通知是否通过邮箱收取
  private Boolean emailNotification;

  // 有消息通知是否通过telegram收取
  // 文档上写的可以通过username跟userId发送消息，但测试结果是只能通过userId发送
  // 难道我调接口的姿势不对？待我后面再收拾它。。
  // private Boolean telegramNotification;

  public Boolean getEmailNotification() {
    return emailNotification;
  }

  public void setEmailNotification(Boolean emailNotification) {
    this.emailNotification = emailNotification;
  }

  public String getTelegramName() {
    return telegramName;
  }

  public void setTelegramName(String telegramName) {
    this.telegramName = telegramName;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", telegramName='" + telegramName + '\'' +
        ", avatar='" + avatar + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", website='" + website + '\'' +
        ", bio='" + bio + '\'' +
        ", score=" + score +
        ", inTime=" + inTime +
        ", token='" + token + '\'' +
        ", emailNotification=" + emailNotification +
        '}';
  }
}
