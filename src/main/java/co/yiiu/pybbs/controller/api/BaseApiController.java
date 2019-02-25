package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.controller.front.BaseController;
import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

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

  // 因为大部分地方用到的这个方法都是token必须要传且正确的，所以这里重载一下getApiUser方法，默认传true
  protected User getApiUser() {
    return getApiUser(true);
  }

  // 接口路由从request里拿token，通过请求UserService获取用户的信息
  // required: boolean 判断是否必须要token，因为有的接口token是非必须的，但如果传了token就可以多返回一些信息
  protected User getApiUser(boolean required) {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    String token = request.getHeader("token");
//    String token = request.getParameter("token");
    if (required) { // token必须要
      // 判断token是否存在，不存在要抛异常
      ApiAssert.notEmpty(token, "token不能为空");
      // 用token查用户信息，查不到要抛异常
      User user = userService.selectByToken(token);
      ApiAssert.notNull(user, "token不正确，请在网站上登录自己的帐号，然后进入个人设置页面扫描二维码获取token");
      return user;
    } else { // token非必须
      // 先判断token存在不，不存在直接返回null
      if (StringUtils.isEmpty(token)) return null;
      // 如果token存在，直接查询用户信息，不管查到查不到，都直接返回
      return userService.selectByToken(token);
    }
  }
}
