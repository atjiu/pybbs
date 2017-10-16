package co.yiiu.module.code.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tomoya on 17-6-6.
 */
@Entity
@Table(name = "yiiu_code")
public class Code implements Serializable {

  @Id
  @GeneratedValue
  private int id;

  @Column(unique = true)
  private String code;

  @Column(name = "expire_time")
  private Date expireTime;

  private String type;

  @Column(name = "is_used")
  private boolean isUsed;

  private String email;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Date expireTime) {
    this.expireTime = expireTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isUsed() {
    return isUsed;
  }

  public void setUsed(boolean used) {
    isUsed = used;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
