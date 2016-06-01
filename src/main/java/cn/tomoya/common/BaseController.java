package cn.tomoya.common;

import cn.tomoya.module.user.User;
import cn.tomoya.utils.Result;
import cn.tomoya.utils.StrUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
 */
public class BaseController extends Controller {

    static {
        PropKit.use("config.properties");
    }

    public Integer defaultPageSize() {
        return StrUtil.str2int(PropKit.get("pageSize"));
    }

    public void success() {
        success(null);
    }

    public void success(Object object) {
        renderJson(new Result(Constants.CODE_SUCCESS, Constants.DESC_SUCCESS, object));
    }

    public void error(String message) {
        renderJson(new Result(Constants.CODE_FAILURE, message, null));
    }

    /**
     * 根据cacheName, cacheKey来清除缓存
     * cacheName 必填，cacheKey选填，不填的话为null
     *
     * @param cacheName
     * @param cacheKey
     */
    public void clearCache(String cacheName, Object cacheKey) {
        if (cacheKey == null) {
            CacheKit.removeAll(cacheName);
        } else {
            CacheKit.remove(cacheName, cacheKey);
        }
    }

    public User getUser() {
        String user_cookie = getCookie(Constants.USER_ACCESS_TOKEN);

        if(StrUtil.notBlank(user_cookie)) {
            return User.me.findByAccessToken(StrUtil.getDecryptToken(user_cookie));
        }
        return null;
    }

}
