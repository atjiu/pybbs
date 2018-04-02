package co.yiiu.core.base;

import co.yiiu.core.exception.ApiAssert;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseController {

  @Autowired private BaseEntity baseEntity;

  /**
   * 带参重定向
   *
   * @param path
   * @return
   */
  protected String redirect(String path) {
    return "redirect:" + path;
  }

  /**
   * 不带参重定向
   *
   * @param response
   * @param path
   * @return
   */
  protected String redirect(HttpServletResponse response, String path) {
    try {
      response.sendRedirect(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
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
