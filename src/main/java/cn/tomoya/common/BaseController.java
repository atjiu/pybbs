package cn.tomoya.common;

import cn.tomoya.module.user.User;
import cn.tomoya.utils.Result;
import cn.tomoya.utils.StrUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class BaseController extends Controller {

    // 接口返回状态码
    private static final String CODE_SUCCESS = "200";
    private static final String CODE_FAILURE = "201";
    private static final String DESC_SUCCESS = "success";

    static {
        PropKit.use("config.properties");
    }

    public void success() {
        success(null);
    }

    public void success(Object object) {
        renderJson(new Result(CODE_SUCCESS, DESC_SUCCESS, object));
    }

    public void error(String message) {
        renderJson(new Result(CODE_FAILURE, message, null));
    }

    /**
     * 删除redis里的缓存
     * @param key
     */
    protected void clearCache(String key) {
        Cache cache = Redis.use();
        cache.del(key);
    }

    public User getUser() {
        String user_cookie = getCookie(Constants.USER_ACCESS_TOKEN);

        if(StrUtil.notBlank(user_cookie)) {
            return User.me.findByAccessToken(StrUtil.getDecryptToken(user_cookie));
        }
        return null;
    }

    public User getUserByToken() {
        String token = getPara("token");
        if(StrUtil.notBlank(token)) {
            return User.me.findByAccessToken(token);
        }
        return null;
    }

}
