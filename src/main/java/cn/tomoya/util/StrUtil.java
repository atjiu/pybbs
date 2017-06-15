package cn.tomoya.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class StrUtil {

  static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
      'e', 'f'};
  static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  static final Random random = new Random();
  static final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

  public static boolean isEmail(String email) {
    if (StringUtils.isEmpty(email)) {
      return false;
    } else {
      Pattern pattern = Pattern.compile(check);
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
    }
  }

  /**
   * 随机指定长度的字符串
   *
   * @param length
   * @return
   */
  public static String randomString(int length) {
    StringBuffer sb = new StringBuffer();
    for (int loop = 0; loop < length; ++loop) {
      sb.append(hexDigits[random.nextInt(hexDigits.length)]);
    }
    return sb.toString();
  }

  /**
   * 随机指定长度的数字
   *
   * @param length
   * @return
   */
  public static String randomNumber(int length) {
    StringBuffer sb = new StringBuffer();
    for (int loop = 0; loop < length; ++loop) {
      sb.append(digits[random.nextInt(digits.length)]);
    }
    return sb.toString();
  }

  /**
   * 检测是否是用户accessToken
   */
  public static boolean isUUID(String accessToken) {
    if (StringUtils.isEmpty(accessToken)) {
      return false;
    } else {
      try {
        // noinspection ResultOfMethodCallIgnored
        UUID.fromString(accessToken);
        return true;
      } catch (Exception e) {
        return false;
      }
    }
  }

  /**
   * Get cookie value from request
   * @param request
   * @param name
   * @return
   */
  public static String getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    for(Cookie cookie: cookies) {
      if(cookie.getName().equals(name)) {
        return cookie.getValue();
      }
    }
    return null;
  }

  /**
   * Set cookie
   * @param response
   * @param name
   * @param value
   * @param params maxAge, httpOnly, domain, path
   */
  public static void setCookie(HttpServletResponse response, String name, String value, Object... params) {
    Cookie cookie = new Cookie(name, value);
    if(params.length == 1) cookie.setMaxAge((Integer) params[0]); //seconds
    if(params.length == 2) cookie.setHttpOnly((Boolean) params[1]);
    if(params.length == 3) cookie.setDomain((String) params[2]);
    if(params.length == 4) cookie.setPath((String) params[3]);
    if(params.length == 5) cookie.setSecure((Boolean) params[4]);
    response.addCookie(cookie);
  }

}
