package co.yiiu.module.code.pojo;

import java.util.Date;

public class Code {
  private Integer id;

  private String code;

  private String email;

  private Date expireTime;

  private Boolean isUsed;

  private String mobile;

  private String type;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code == null ? null : code.trim();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  public Date getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Date expireTime) {
    this.expireTime = expireTime;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile == null ? null : mobile.trim();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type == null ? null : type.trim();
  }
}