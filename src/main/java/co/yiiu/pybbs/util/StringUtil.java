package co.yiiu.pybbs.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://atjiu.github.io
 */
public class StringUtil {

    private StringUtil() {
    }

    // 手机号正则
    public static final String MOBILEREGEX = "^1[0-9]{10}";
    // email正则
    public static final String EMAILREGEX = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    // url正则
    public static final String URLREGEX = "^((https|http)?:\\/\\/)[^\\s]+";
    // 用户名正则
    public static final String USERNAMEREGEX = "[a-z0-9A-Z]{2,16}";
    // 密码正则
    public static final String PASSWORDREGEX = "[a-z0-9A-Z]{6,32}";
    // 生成随机字符串用到的字符数组
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z'};
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

    /**
     * 将字符串转化成unicode码
     *
     * @param string
     * @return
     * @author shuai.ding
     */
    public static String string2Unicode(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        char[] bytes = string.toCharArray();
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            char c = bytes[i];
            // 标准ASCII范围内的字符，直接输出
            if (c >= 0 && c <= 127) {
                unicode.append(c);
                continue;
            }
            String hexString = Integer.toHexString(bytes[i]);
            unicode.append("\\u");
            // 不够四位进行补0操作
            if (hexString.length() < 4) {
                unicode.append("0000".substring(hexString.length(), 4));
            }
            unicode.append(hexString);
        }
        return unicode.toString();
    }

    /**
     * \s+是空格一个或者多个,不管在那个位置都能匹配
     * \pP 其中的小写 p 是 property 的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀。
     * 大写 P 表示 Unicode 字符集七个字符属性之一：标点字符。
     * 其他六个是
     * L：字母；
     * M：标记符号（一般不会单独出现）；
     * Z：分隔符（比如空格、换行等）；
     * S：符号（比如数学符号、货币符号等）；
     * N：数字（比如阿拉伯数字、罗马数字等）；
     * C：其他字符
     *
     * @param str
     * @return
     */
    public static String removeSpecialChar(String str) {
        String regEx = "\\pP|\\pS|\\s+";
        str = Pattern.compile(regEx).matcher(str).replaceAll("").trim();
        return str;
    }

}
