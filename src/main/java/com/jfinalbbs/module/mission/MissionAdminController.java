package com.jfinalbbs.module.mission;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/mission", viewPath = "page/admin/mission")
public class MissionAdminController extends BaseController {

    @RequiresPermissions("menu:mission")
    public void index() {
        setAttr("page", Mission.me.paginate(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }
}
