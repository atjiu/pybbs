package co.yiiu.pybbs.interceptor;

import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.CookieUtil;
import co.yiiu.pybbs.util.HttpUtil;
import co.yiiu.pybbs.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

  private Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
  @Autowired
  private UserService userService;
  @Autowired
  private CookieUtil cookieUtil;
  @Autowired
  private SystemConfigService systemConfigService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    long start = System.currentTimeMillis();
    request.setAttribute("_start", start);

    // 判断session里有用户信息，有直接通过
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("_user");
    if (user == null) {
      // 获取cookie里的token，查询用户的信息并放入session里
      String token = cookieUtil.getCookie(systemConfigService.selectAllConfig().get("cookie_name").toString());
      if (!StringUtils.isEmpty(token)) {
        // 根据token查询用户是否存在
        user = userService.selectByToken(token);
        if (user != null) {
          // 用户存在写session，cookie然后给予通过
          session.setAttribute("_user", user);
          cookieUtil.setCookie(systemConfigService.selectAllConfig().get("cookie_name").toString(), user.getToken());
        }
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    if (!HttpUtil.isApiRequest(request) && modelAndView != null) {
      modelAndView.addObject("site", systemConfigService.selectAllConfig());
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
