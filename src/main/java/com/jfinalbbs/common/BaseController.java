package com.jfinalbbs.common;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.Result;
import com.jfinalbbs.utils.StrUtil;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class BaseController extends Controller {

    public String baseUrl() {
        return Constants.getValue("baseUrl");
    }

    public Integer defaultPageSize() {
        return StrUtil.str2int(Constants.getValue("pageSize"));
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

    /**
     * 根据用户令牌获取用户信息
     * @param token
     * @return
     */
    public User getUser(String token) {
        return User.me.findByToken(token);
    }
}
