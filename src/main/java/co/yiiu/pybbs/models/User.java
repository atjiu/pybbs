package co.yiiu.pybbs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya at 2018/9/3
 */
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  private String username;
  private String avatar;
  @JsonIgnore
  private String password;
  private String email;
  private String website;
  private String bio;
  private Integer score;
  private Date inTime;

  // 是否是管理员
  @Transient
  private boolean admin;

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

}
