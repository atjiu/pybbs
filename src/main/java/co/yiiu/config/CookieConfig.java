package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomoya on 17-6-15.
 */
@Getter
@Setter
public class CookieConfig {

  private String domain;
  private String userName;
  private Integer userMaxAge;

  private String adminUserName;
  private Integer adminUserMaxAge;

}
