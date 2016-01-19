package com.jfinalbbs.common;

import com.jfinal.kit.PropKit;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Constants {

    public static final String UPLOAD_DIR = "static/upload";
    public static final String UPLOAD_DIR_AVATAR = "avatar";

    public static final String ERROR = "error";
    public static final String USER_COOKIE = "user_token_v2";
    public static final String USER_SESSION = "user";
    public static final String BEFORE_URL = "before_url";
    public static final String ADMIN_BEFORE_URL = "admin_before_url";
    public static final String SESSION_ADMIN_USER = "admin_user";
    public static final String TODAY = "today";
    public static final String NOTIFICATION_MESSAGE = "有人@你";
    public static final String NOTIFICATION_MESSAGE1 = "回复了你的话题";

    public static final String OP_ERROR_MESSAGE = "非法操作";
    public static final String DELETE_FAILURE = "删除失败";

    public static final String COOKIE_ADMIN_TOKEN = "admin_user_token";

    public static String getBaseUrl() {
        return PropKit.use("config.properties").get("base.url");
    }

    public static String getSiteTitle() {
        return PropKit.use("config.properties").get("site_title");
    }

    public static class ResultCode {
        public static final String SUCCESS = "200";
        public static final String FAILURE = "201";
    }

    public static class ResultDesc {
        public static final String SUCCESS = "success";
        public static final String FAILURE = "error";
    }

    public static class RequestMethod {
        public static final String GET = "get";
        public static final String POST = "post";
    }

    public static class CacheName {
        public static final String SECTIONLIST = "section_list";
        public static final String SECTIONSHOWLIST = "section_show_list";
        public static final String LINKLIST = "link_list";
        public static final String DEFAULTSECTION = "default_section";
    }

    public static class CacheKey {
        public static final String SECTIONLISTKEY = "section_list_key";
        public static final String SECTIONSHOWLISTKEY = "section_show_list_key";
        public static final String LINKLISTKEY = "link_list_key";
        public static final String DEFAULTSECTIONKEY = "default_section_key";
    }

    public static class ThirdLogin {
        public static final String QQ = "qq";
        public static final String SINA = "sina";
        public static final String WECHAT = "wechat";
    }

    public static class ValiCodeType {
        public static final String FORGET_PWD = "forgetpwd";
        public static final String REG = "reg";
    }

}
