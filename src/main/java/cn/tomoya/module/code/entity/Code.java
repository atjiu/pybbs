package cn.tomoya.module.code.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomoya on 17-6-6.
 */
@Entity
@Table(name = "pybbs_code")
public class Code {

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
}
