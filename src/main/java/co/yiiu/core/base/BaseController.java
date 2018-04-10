package co.yiiu.core.base;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseController {

  @Autowired private BaseEntity baseEntity;
  @Autowired private SiteConfig siteConfig;

  /**
   * 带参重定向
   *
   * @param path
   * @return
   */
  protected String redirect(String path) {
    String baseUrl = siteConfig.getBaseUrl();
    baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
    return "redirect:" + baseUrl + path;
  }

  /**
   * 获取用户信息
   *
   * @return 没登录返回错误信息
   */
  protected User getUser() {
    return baseEntity.getUser();
  }

  protected AdminUser getAdminUser() {
    return baseEntity.getAdminUser();
  }

  /**
   * 获取用户信息
   *
   * @return 没登录返回json
   */
  protected User getApiUser() {
    User user = baseEntity.getUser();
    ApiAssert.notNull(user, "请先登录");
    return user;
  }

}
