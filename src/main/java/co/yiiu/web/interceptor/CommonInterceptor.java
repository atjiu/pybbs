package co.yiiu.web.interceptor;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.util.IpUtil;
import co.yiiu.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

  private Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

  @Autowired
  private SiteConfig siteConfig;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    long start = System.currentTimeMillis();
    request.setAttribute("_start", start);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) throws Exception {
    String attendanceValue = StrUtil.getCookie(request, siteConfig.getCookie().getAttendanceName());
    if (StringUtils.isEmpty(attendanceValue)) attendanceValue = "-1";
    request.setAttribute("_attendance", attendanceValue);// send attendance value to page
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
