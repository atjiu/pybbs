package cn.tomoya.config.base;

import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class BaseController {

  Logger log = Logger.getLogger(BaseController.class);

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private UserService userService;

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
   * 渲染页面
   *
   * @param path 前面必须要加上 /
   * @return
   */
  protected String render(String path) {
    return siteConfig.getTheme() + path;
  }

  protected String renderText(HttpServletResponse response, String msg) {
    try {
      response.setContentType("text/html;charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获取Security用户
   *
   * @return
   */
  protected UserDetails getSecurityUser() {
    Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    boolean b = o instanceof UserDetails;
    if (b) {
      log.info(o.toString());
      return (UserDetails) o;
    }
    return null;
  }

  /**
   * 获取用户信息
   *
   * @return
   */
  protected User getUser() {
    UserDetails userDetails = getSecurityUser();
    if (userDetails != null) {
      return userService.findByUsername(userDetails.getUsername());
    }
    return null;
  }

  /**
   * 获取用户信息
   * @param token
   * @return
   */
  protected User getUser(String token) {
    if (StringUtils.isEmpty(token)) {
      return null;
    } else {
      return userService.findByToken(token);
    }
  }

}
