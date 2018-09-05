package co.yiiu.pybbs.models;

import java.util.Collection;
import java.util.Date;

public class JwtUser {

  private String id;
  private String username;
  private String password;
  private Collection<String> authorities;
  private Date lastPasswordResetDate;

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

  public Collection<String> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<String> authorities) {
    this.authorities = authorities;
  }

  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  public void setLastPasswordResetDate(Date lastPasswordResetDate) {
    this.lastPasswordResetDate = lastPasswordResetDate;
  }
}