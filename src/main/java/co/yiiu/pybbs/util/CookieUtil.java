package co.yiiu.pybbs.util;

import co.yiiu.pybbs.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class CookieUtil {

  @Autowired
  private SystemConfigService systemConfigService;

  public void setCookie(String key, String value) {
    HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    Cookie e = new Cookie(key, value);
    e.setPath("/");
    e.setMaxAge(Integer.parseInt(systemConfigService.selectAllConfig().get("cookie_max_age").toString()));
    e.setDomain(systemConfigService.selectAllConfig().get("cookie_domain").toString());
    assert response != null;
    response.addCookie(e);
  }


  public String getCookie(String name) {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie c : cookies) {
        if (c.getName().equalsIgnoreCase(name)) {
          return c.getValue();
        }
      }
    }
    return null;
  }

  // 清除cookie
  public void clearCookie(String name) {
    HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(-1);
    cookie.setDomain((String) systemConfigService.selectAllConfig().get("cookie_domain"));
    cookie.setPath("/");
    assert response != null;
    response.addCookie(cookie);
  }
}
