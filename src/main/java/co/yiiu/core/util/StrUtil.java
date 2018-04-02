package co.yiiu.core.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class StrUtil {

  private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
      'e', 'f'};
  private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  public static final Random random = new Random();
  public static final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
  public static final String userNameCheck = "[a-z0-9A-Z]{2,16}";

  public static boolean check(String text, String regex) {
    if (StringUtils.isEmpty(text)) {
      return false;
    } else {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
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
    StringBuilder sb = new StringBuilder();
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
    StringBuilder sb = new StringBuilder();
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
   * remove duplicate
   *
   * @param strs
   * @return
   */
  public static List<String> removeDuplicate(String[] strs) {
    List<String> list = new ArrayList<>();
    for (String s : strs) {
      if (!list.contains(s)) {
        list.add(s);
      }
    }
    return list;
  }

  // 格式化url参数部分返回map
  // params格式：a=1&b=2&c=3
  // 返回：{a: 1, b: 2, c: 3}
  public static Map<String, Object> formatParams(String params) {
    if(StringUtils.isEmpty(params)) return null;
    Map<String, Object> map = new HashMap<>();
    for(String s : params.split("&")) {
      String[] ss = s.split("=");
      map.put(ss[0], ss[1]);
    }
    return map;
  }

}
