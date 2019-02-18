package co.yiiu.pybbs.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public class StringUtil {

  private StringUtil() {}

  // email正则
  public static final String EMAILREGEX = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
  // url正则
  public static final String URLREGEX = "^((https|http)?:\\/\\/)[^\\s]+";
  // 用户名正则
  public static final String USERNAMEREGEX = "[a-z0-9A-Z]{2,16}";
  // 密码正则
  public static final String PASSWORDREGEX = "[a-z0-9A-Z]{6,32}";
  // 生成随机字符串用到的字符数组
  private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
      'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
  // 生成随机长度的数字用到的数组
  private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  public static final Random random = new Random();

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

  // 生成一个uuid
  public static String uuid() {
    return UUID.randomUUID().toString();
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

  // 格式化url参数部分返回map
  // params格式：a=1&b=2&c=3
  // 返回：{a: 1, b: 2, c: 3}
  public static Map<String, Object> formatParams(String params) {
    if (StringUtils.isEmpty(params)) return null;
    Map<String, Object> map = new HashMap<>();
    for (String s : params.split("&")) {
      String[] ss = s.split("=");
      map.put(ss[0], ss[1]);
    }
    return map;
  }

  // 查找评论里at的用户名
  public static List<String> fetchAtUser(String content) {
    if (StringUtils.isEmpty(content)) return Collections.emptyList();
    // 去掉 ``` ``` 包围的内容
    content = content.replaceAll("```([\\s\\S]*)```", "");
    // 去掉 ` ` 包围的内容
    content = content.replaceAll("`([\\s\\S]*)`", "");
    // 找到@的用户
    String atRegex = "@[a-z0-9-_]+\\b?";
    List<String> atUsers = new ArrayList<>();
    Pattern regex = Pattern.compile(atRegex);
    Matcher regexMatcher = regex.matcher(content);
    while (regexMatcher.find()) {
      atUsers.add(regexMatcher.group());
    }
    return atUsers;
  }

  // 去掉数组里的空值和重复数据
  public static Set<String> removeEmpty(String[] strs) {
    if (strs == null || strs.length == 0) return Collections.emptySet();
    Set<String> set = new HashSet<>();
    for (String str : strs) {
      if (!StringUtils.isEmpty(str)) {
        set.add(str);
      }
    }
    return set;
  }

}
