package com.jfinalbbs.system;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;

import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class SysConfigAdminController extends BaseController {

    public void index() {
        Map<String, Object> map = SysConfig.me.findAll2Map();
        setAttrs(map);
        render("index.ftl");
    }

    /**
     * 更新系统设置
     */
    public void save() {
        SysConfig.me.update("siteTitle", getPara("siteTitle"));
        SysConfig.me.update("baseUrl", getPara("baseUrl"));
        SysConfig.me.update("pageSize", getPara("pageSize"));
        SysConfig.me.update("qq_appId", getPara("qq_appId"));
        SysConfig.me.update("qq_appKey", getPara("qq_appKey"));
        SysConfig.me.update("qq_redirect_URI", getPara("qq_redirect_URI"));
        SysConfig.me.update("sina_clientId", getPara("sina_clientId"));
        SysConfig.me.update("sina_clientSercret", getPara("sina_clientSercret"));
        SysConfig.me.update("emailSmtp", getPara("emailSmtp"));
        SysConfig.me.update("emailSender", getPara("emailSender"));
        SysConfig.me.update("emailUsername", getPara("emailUsername"));
        SysConfig.me.update("emailPassword", getPara("emailPassword"));
        SysConfig.me.update("qq_meta", getPara("qq_meta"));
        SysConfig.me.update("sina_meta", getPara("sina_meta"));
        SysConfig.me.update("baidu_site_meta", getPara("baidu_site_meta"));
        SysConfig.me.update("google_site_meta", getPara("google_site_meta"));
        SysConfig.me.update("bing_site_meta", getPara("bing_site_meta"));
        SysConfig.me.update("beian_name", getPara("beian_name"));
        SysConfig.me.update("sina_redirect_URI", getPara("sina_redirect_URI"));
        clearCache(Constants.SYSCONFIGCACHE, Constants.SYSCONFIGCACHEKEY);
        redirect(baseUrl() + "/admin/sysconfig");
    }
}
