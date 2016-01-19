package com.jfinalbbs.mission;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(AdminUserInterceptor.class)
public class MissionAdminController extends BaseController {

    public void index() {
        setAttr("page", Mission.me.paginate(getParaToInt("p", 1), PropKit.use("config.properties").getInt("page_size")));
        render("index.html");
    }
}
