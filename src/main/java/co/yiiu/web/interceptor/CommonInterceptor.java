package co.yiiu.web.interceptor;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.util.CookieHelper;
import co.yiiu.core.util.IpUtil;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Slf4j
public class CommonInterceptor implements HandlerInterceptor {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    long start = System.currentTimeMillis();
    request.setAttribute("_start", start);

    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    if(user == null) {
      String token = CookieHelper.getValue(request, siteConfig.getCookie().getUserName());
      if(StringUtils.isEmpty(token)) return true;
      try {
        token = new String(Base64Helper.decode(token));
      } catch (Exception e) {
        log.error(e.getLocalizedMessage());
        CookieHelper.clearCookieByName(response, siteConfig.getCookie().getUserName());
        return true;
      }
      user = userService.findByToken(token);
      if(user == null) {
        CookieHelper.clearCookieByName(response, siteConfig.getCookie().getUserName());
      } else {
        session.setAttribute("user", user);
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    long start = (long) request.getAttribute("_start");

    String actionName = request.getRequestURI();
    String clientIp = IpUtil.getIpAddr(request);
    StringBuilder logstring = new StringBuilder();
    logstring.append(clientIp).append("|").append(actionName).append("|");
    Map<String, String[]> parmas = request.getParameterMap();
    parmas.forEach((key, value) -> {
      logstring.append(key);
      logstring.append("=");
      for (String paramString : value) {
        logstring.append(paramString);
      }
      logstring.append("|");
    });
    long executionTime = System.currentTimeMillis() - start;
    logstring.append("excutetime=").append(executionTime).append("ms");
    log.info(logstring.toString());
  }
}
