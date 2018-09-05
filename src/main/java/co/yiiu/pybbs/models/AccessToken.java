package co.yiiu.pybbs.models;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class AccessToken implements Serializable {

  @Id
  private String id;
  private String token;
  private String userId;
  private Date inTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }
}
