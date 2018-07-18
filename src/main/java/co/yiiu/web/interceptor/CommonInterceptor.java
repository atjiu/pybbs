package co.yiiu.web.interceptor;

import co.yiiu.core.base.BaseEntity;
import co.yiiu.core.util.IpUtil;
import co.yiiu.module.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  private BaseEntity baseEntity;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    long start = System.currentTimeMillis();
    request.setAttribute("_start", start);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) {
    if(modelAndView != null) {
      User user = baseEntity.getUser();
      if (user != null) modelAndView.addObject("user", user);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    long start = (long) request.getAttribute("_start");
    String actionName = request.getRequestURI();
    String clientIp = IpUtil.getIpAddr(request);
    StringBuilder logString = new StringBuilder();
    logString.append(clientIp).append("|").append(actionName).append("|");
    Map<String, String[]> params = request.getParameterMap();
    params.forEach((key, value) -> {
      logString.append(key);
      logString.append("=");
      for (String paramString : value) {
        logString.append(paramString);
      }
      logString.append("|");
    });
    long executionTime = System.currentTimeMillis() - start;
    logString.append("excitation=").append(executionTime).append("ms");
    log.info(logString.toString());
  }
}
