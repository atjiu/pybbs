package co.yiiu.config.properties;

/**
 * Created by tomoya on 17-6-15.
 */
public class CookieConfig {

  private String domain;
  private String userName;
  private Integer userMaxAge;

  private String adminUserName;
  private Integer adminUserMaxAge;

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getUserMaxAge() {
    return userMaxAge;
  }

  public void setUserMaxAge(Integer userMaxAge) {
    this.userMaxAge = userMaxAge;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public Integer getAdminUserMaxAge() {
    return adminUserMaxAge;
  }

  public void setAdminUserMaxAge(Integer adminUserMaxAge) {
    this.adminUserMaxAge = adminUserMaxAge;
  }
}
