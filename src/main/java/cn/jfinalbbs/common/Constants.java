package cn.jfinalbbs.common;

import com.jfinal.kit.PropKit;

/**
 * Created by liuyang on 15/4/2.
 */
public class Constants {

    public static final String ERROR = "error";
    public static final String USER_COOKIE = "user_token_v2";
    public static final String USER_SESSION = "user";
    public static final String BEFORE_URL = "before_url";
    public static final String ADMIN_BEFORE_URL = "admin_before_url";
    public static final String SESSION_ADMIN_USER = "admin_user";
    public static final String TODAY = "today";
    public static final String NOTIFICATION_MESSAGE1 = "回复了你的话题";
    public static final String NOTIFICATION_MESSAGE2 = "引用了你的回复";

    public static final String OP_ERROR_MESSAGE = "非法操作";
    public static final String DELETE_FAILURE = "删除失败";

    public static final String COOKIE_ADMIN_TOKEN = "admin_user_token";
    public static final String COOKIE_EMAIL = "email";

    public static String getBaseUrl() {
        return PropKit.use("config.properties").get("base.url");
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

    public static class SystemCode {
        public static final String TYPE_SEARCH_PASS = "search_pass";
    }

    public static class CacheName {
        public static final String SECTIONLIST = "section_list";
        public static final String LINKLIST = "link_list";
    }

    public static class CacheKey {
        public static final String SECTIONLISTKEY = "section_list_key";
        public static final String LINKLISTKEY = "link_list_key";
    }

}
