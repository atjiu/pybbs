package co.yiiu.pybbs.interceptor;

import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class UserApiInterceptor implements HandlerInterceptor {

  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getParameter("token");
    User user = userService.selectByToken(token);
    if (user == null) {
      HttpUtil.responseWrite(request, response);
      return false;
    } else {
      return true;
    }
  }

}
