package com.jfinalbbs.module.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseModel;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class AdminUser extends BaseModel<AdminUser> {

    public final static AdminUser me = new AdminUser();

    public Page<AdminUser> page(Integer pageNumber, Integer pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from jfbbs_admin_user order by in_time desc");
    }

    public AdminUser findByUsername(String username) {
        return super.findFirst("select * from jfbbs_admin_user where username = ?", username);
    }

    public void correlationRole(Integer userId, Integer[] roles) {
        //先删除已经存在的关联
        Db.update("delete from jfbbs_user_role where uid = ?", userId);
        //建立新的关联关系
        for(Integer rid : roles) {
            UserRole userRole = new UserRole();
            userRole.set("uid", userId)
                    .set("rid", rid)
                    .save();
        }
    }
}
