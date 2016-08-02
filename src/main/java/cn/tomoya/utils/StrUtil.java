package cn.tomoya.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.kit.StrKit;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class StrUtil extends StrKit {

    static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static final Random rand = new Random();
    static final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmail(String email) {
        if (isBlank(email)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(check);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String randomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int loop = 0; loop < length; ++loop) {
            sb.append(hexDigits[rand.nextInt(hexDigits.length)]);
        }
        return sb.toString();
    }

    public static String randomNumber(int length) {
        StringBuffer sb = new StringBuffer();
        for (int loop = 0; loop < length; ++loop) {
            sb.append(digits[rand.nextInt(digits.length)]);
        }
        return sb.toString();
    }

    public static String getEncryptionToken(String token) {
        for (int i = 0; i < 6; i++) {
            token = EncryptionUtil.encoderBase64(token.getBytes());
        }
        return token;
    }

    public static String getDecryptToken(String encryptionToken) {
        for (int i = 0; i < 6; i++) {
            encryptionToken = EncryptionUtil.decoderBase64(encryptionToken.getBytes());
        }
        return encryptionToken;
    }

    public static String noHtml(String s) {
        if (isBlank(s)) return "";
        else return s.replaceAll("<[.[^<]]*>", "");
    }

    public static String transHtml(String s) {
        if (isBlank(s)) return "";
        else return s.replace("<", "&lt;").replace(">", "&gt;");
    }

    public static List<String> fetchUsers(String str) {
        List<String> ats = new ArrayList<String>();
        String pattern = "@([a-zA-Z_0-9-/b]+)\\s";
        Pattern regex = Pattern.compile(pattern);
        Matcher regexMatcher = regex.matcher(str);
        while (regexMatcher.find()) {
            ats.add(regexMatcher.group(1));
        }
        return ats;
    }

    public static int str2int(String s) {
        if (s == null || s.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    /**
     * json字符串转换成 Map
     * 依赖Gson包
     */
    public static Map<String, Object> parseToMap(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    public static void main(String[] args) {
        System.out.println(getEncryptionToken("715e5dc73a624e36bac1e12955634bd8"));
    }

}
