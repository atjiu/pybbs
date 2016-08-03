package cn.tomoya.common;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Constants {

    // COOKIE SESSION变量KEY
    public static final String USER_ACCESS_TOKEN = "user_access_token";
    // 接口返回描述
    public static final String OP_ERROR_MESSAGE = "非法操作";

    //缓存的key
    public enum CacheEnum {
        section,
        sections,
        topic,
        topicappends,
        usernickname,
        useraccesstoken,
        userpermissions,
        collect,
        collects,
        collectcount,
        usercollectcount
    }

    //第三方登录渠道
    public enum LoginEnum {
        Github,
        Weibo
    }

}