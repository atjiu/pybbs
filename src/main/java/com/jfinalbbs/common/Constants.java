package com.jfinalbbs.common;

import com.jfinalbbs.system.SysConfig;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Constants {

    // 系统变量KEY
    public static final String UPLOAD_DIR = "/Users/tomoya/Desktop/static/upload";
    public static final String UPLOAD_DIR_AVATAR = "avatar";
    public static final String NOTIFICATION_MESSAGE = "有人@你";
    public static final String NOTIFICATION_MESSAGE1 = "回复了你的话题";

    // COOKIE SESSION变量KEY
    public static final String USER_COOKIE = "user_token_v2";
    public static final String USER_SESSION = "user";
    public static final String BEFORE_URL = "before_url";
    public static final String ADMIN_BEFORE_URL = "admin_before_url";
    public static final String SESSION_ADMIN_USER = "admin_user";
    public static final String COOKIE_ADMIN_TOKEN = "admin_user_token";

    // 接口返回状态码
    public static final String CODE_SUCCESS = "200";
    public static final String CODE_FAILURE = "201";

    // 接口返回描述
    public static final String DESC_SUCCESS = "success";
    public static final String DESC_FAILURE = "error";
    public static final String OP_ERROR_MESSAGE = "非法操作";
    public static final String DELETE_FAILURE = "删除失败";

    // http请求方式
    public static final String GET = "get";
    public static final String POST = "post";

    // 缓存名称
    public static final String SECTIONLIST = "section_list";
    public static final String SECTIONSHOWLIST = "section_show_list";
    public static final String LINKLIST = "link_list";
    public static final String DEFAULTSECTION = "default_section";
    public static final String SYSCONFIGCACHE = "sysconfigcache";

    // 缓存KEY
    public static final String SECTIONLISTKEY = "section_list_key";
    public static final String SECTIONSHOWLISTKEY = "section_show_list_key";
    public static final String LINKLISTKEY = "link_list_key";
    public static final String DEFAULTSECTIONKEY = "default_section_key";
    public static final String SYSCONFIGCACHEKEY = "sysconfigcachekey";

    // 第三方
    public static final String QQ = "qq";
    public static final String SINA = "sina";

    // 验证码类型
    public static final String FORGET_PWD = "forgetpwd";
    public static final String REG = "reg";

    public static String getValue(String key) {
        return SysConfig.me.findByKey(key);
    }

}
