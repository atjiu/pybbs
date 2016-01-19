package com.jfinalbbs.user;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class AdminUser extends Model<AdminUser> {

    public final static AdminUser me = new AdminUser();

    //根据用户名，密码登录
    public AdminUser login(String username, String password) {
        return super.findFirst("select * from admin_user where username = ? and password = ?", username, password);
    }
}
