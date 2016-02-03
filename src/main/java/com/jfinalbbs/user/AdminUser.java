package com.jfinalbbs.user;

import com.jfinalbbs.common.BaseModel;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class AdminUser extends BaseModel<AdminUser> {

    public final static AdminUser me = new AdminUser();

    //根据用户名，密码登录
    public AdminUser login(String username, String password) {
        return super.findFirst("select * from jfbbs_admin_user where username = ? and password = ?", username, password);
    }
}
