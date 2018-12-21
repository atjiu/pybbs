package co.yiiu.pybbs.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class HttpUtil {

  public static boolean isApiRequest(HttpServletRequest request) {
    return !request.getHeader("Accept").contains("text/html");
  }
}
