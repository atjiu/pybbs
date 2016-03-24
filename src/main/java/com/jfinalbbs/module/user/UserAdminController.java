package com.jfinalbbs.module.user;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/user", viewPath = "page/admin/user")
public class UserAdminController extends BaseController {

    @RequiresPermissions("menu:user")
    public void index() {
        String nickname = getPara("nickname");
        String email = getPara("email");
        setAttr("page", User.me.page(getParaToInt("p", 1), defaultPageSize(), nickname, email));
        setAttr("nickname", nickname);
        setAttr("email", email);
        render("index.ftl");
    }

    @RequiresPermissions("user:disabled")
    public void disabled() {
        //以后实现
    }

}
