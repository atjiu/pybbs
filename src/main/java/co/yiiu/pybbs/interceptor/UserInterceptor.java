package co.yiiu.pybbs.interceptor;

import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("_user");
    if (user == null) {
      this.responseWrite(request, response);
      return false;
    } else {
      return true;
    }
  }

  // 根据请求接收的类型定义不同的响应方式
  // 判断请求对象request里的header里accept字段接收类型
  // 如果是 text/html 则响应一段js，这里要将response对象的响应内容类型也设置成 text/javascript
  // 如果是 application/json 则响应一串json，response 对象的响应内容类型要设置成 application/json
  // 因为响应内容描述是中文，所以都要带上 ;charset=utf-8 否则会有乱码
  // 写注释真累费劲。。
  private void responseWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String accept = request.getHeader("Accept");
    if (accept.contains("text/html")) {
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().write("<script>alert('请先登录!');window.history.go(-1);</script>");
    } else /*if (accept.contains("application/json"))*/ {
      response.setContentType("application/json;charset=utf-8");
      Result result = new Result();
      result.setCode(201);
      result.setDescription("请先登录");
      response.getWriter().write(JsonUtil.objectToJson(result));
    }
  }
}
