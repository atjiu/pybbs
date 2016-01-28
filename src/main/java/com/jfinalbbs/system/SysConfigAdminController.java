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
        render("index.html");
    }

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
        clearCache(Constants.SYSCONFIGCACHE, Constants.SYSCONFIGCACHEKEY);
        redirect(baseUrl() + "/admin/sysconfig");
    }
}
