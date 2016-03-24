package com.jfinalbbs.module.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinalbbs.common.BaseModel;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class UserRole extends BaseModel<UserRole> {

    public static final UserRole me = new UserRole();

    public List<UserRole> findByUserId(Integer userId) {
        return super.find("select * from jfbbs_user_role where uid = ?", userId);
    }

    public void deleteByUserId(Integer userId) {
        Db.update("delete from jfbbs_user_role where uid = ?", userId);
    }

    public void deleteByRoleId(Integer roleId) {
        Db.update("delete from jfbbs_user_role where rid = ?", roleId);
    }

}
