package com.jfinalbbs.module.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseModel;
import com.jfinalbbs.common.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Role extends BaseModel<Role> {

    public static final Role me = new Role();

    public Page<Role> page(Integer pageNumber, Integer pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from jfbbs_role");
    }

    public List<Role> findAll() {
        return super.find("select * from jfbbs_role");
    }

    public Set<String> findRoles(String username) {
        List<Role> roles = super.findByCache(Constants.SHIROCACHE, Constants.ROLECACHEKEY + username,
                "select r.* from jfbbs_admin_user u, jfbbs_role r, jfbbs_user_role ur where u.id = ur.uid and r.id = ur.rid and u.username = ?", username);
        Set<String> set = new HashSet<String>();
        for(Role r: roles) {
            set.add(r.getStr("name"));
        }
        return set;
    }

    public void correlationPermission(Integer roleId, Integer[] permissionIds) {
        //先删除已经存在的关联
        Db.update("delete from jfbbs_role_permission where rid = ?", roleId);
        //建立新的关联关系
        for(Integer pid : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.set("rid", roleId)
                    .set("pid", pid)
                    .save();
        }
    }
}
