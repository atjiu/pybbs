package com.jfinalbbs.module.user;

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
public class Permission extends BaseModel<Permission> {

    public static final Permission me = new Permission();

    public List<Permission> findByPid(Object pid) {
        return super.find("select * from jfbbs_permission where pid = ?", pid);
    }

    public List<Permission> findAll() {
        List<Permission> permissions = this.findByPid(0);
        for(Permission p: permissions) {
            List<Permission> permissionList = this.findByPid(p.get("id"));
            p.put("childPermission", permissionList);
        }
        return permissions;
    }

    public Set<String> findPermissions(String username) {
        List<Permission> permissions = super.findByCache(Constants.SHIROCACHE, Constants.PERMISSIONCACHEKEY + username,
                "select p.* from jfbbs_admin_user u, jfbbs_role r, jfbbs_permission p, " +
                "jfbbs_user_role ur, jfbbs_role_permission rp where u.id = ur.uid and r.id = ur.rid and r.id = rp.rid and p.id = rp.pid and u.username = ?", username);
        Set<String> set = new HashSet<String>();
        for(Permission p: permissions) {
            set.add(p.getStr("name"));
        }
        return set;
    }
}
