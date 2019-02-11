package co.yiiu.pybbs.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class HttpUtil {

  public static boolean isApiRequest(HttpServletRequest request) {
    return request.getHeader("Accept") == null || !request.getHeader("Accept").contains("text/html");
  }

  // 根据请求接收的类型定义不同的响应方式
  // 判断请求对象request里的header里accept字段接收类型
  // 如果是 text/html 则响应一段js，这里要将response对象的响应内容类型也设置成 text/javascript
  // 如果是 application/json 则响应一串json，response 对象的响应内容类型要设置成 application/json
  // 因为响应内容描述是中文，所以都要带上 ;charset=utf-8 否则会有乱码
  // 写注释真累费劲。。
  public static void responseWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (!HttpUtil.isApiRequest(request)) {
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
