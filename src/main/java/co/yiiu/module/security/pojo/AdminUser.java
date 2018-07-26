package co.yiiu.module.security.pojo;

import java.util.Date;

public class AdminUser {
  private Integer id;

  private Integer attempts;

  private Date attemptsTime;

  private Date inTime;

  private String password;

  private Integer roleId;

  private String token;

  private String username;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAttempts() {
    return attempts;
  }

  public void setAttempts(Integer attempts) {
    this.attempts = attempts;
  }

  public Date getAttemptsTime() {
    return attemptsTime;
  }

  public void setAttemptsTime(Date attemptsTime) {
    this.attemptsTime = attemptsTime;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password == null ? null : password.trim();
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token == null ? null : token.trim();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username == null ? null : username.trim();
  }
}