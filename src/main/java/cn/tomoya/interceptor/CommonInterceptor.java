package cn.tomoya.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.tomoya.util.IpUtil;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

  private Logger log = Logger.getLogger(CommonInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    long start = System.currentTimeMillis();
    request.setAttribute("_start", start);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    long start = (long) request.getAttribute("_start");

    String actionName = request.getRequestURI();
    String clientIp = IpUtil.getIpAddr(request);
    StringBuffer logstring = new StringBuffer();
    logstring.append(clientIp).append("|").append(actionName).append("|");
    Map<String, String[]> parmas = request.getParameterMap();
    Iterator<Map.Entry<String, String[]>> iter = parmas.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<String, String[]> entry = iter.next();
      logstring.append(entry.getKey());
      logstring.append("=");
      for (String paramString : entry.getValue()) {
        logstring.append(paramString);
      }
      logstring.append("|");
    }
    long executionTime = System.currentTimeMillis() - start;
    logstring.append("excutetime=").append(executionTime).append("ms");
    log.info(logstring.toString());
  }
}
