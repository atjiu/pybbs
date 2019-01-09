package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.controller.front.BaseController;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class BaseApiController extends BaseController {

  @Autowired
  private UserService userService;

  protected Result success() {
    return success(null);
  }

  protected Result success(Object detail) {
    Result result = new Result();
    result.setCode(200);
    result.setDescription("SUCCESS");
    result.setDetail(detail);
    return result;
  }

  protected Result error(String description) {
    Result result = new Result();
    result.setCode(201);
    result.setDescription(description);
    return result;
  }

  // 接口路由从request里拿token，通过请求UserService获取用户的信息
  protected User getApiUser() {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    String token = request.getParameter("token");
    return userService.selectByToken(token);
  }
}
