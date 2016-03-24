package com.jfinalbbs.module.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinalbbs.common.BaseModel;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class RolePermission extends BaseModel<RolePermission> {

    public static final RolePermission me = new RolePermission();

    public List<RolePermission> findByRoleId(Integer roleId) {
        return super.find("select * from jfbbs_role_permission where rid = ?", roleId);
    }

    public void deleteByPermissionId(Integer permissionId) {
        Db.update("delete from jfbbs_role_permission where pid = ?", permissionId);
    }

    public void deleteByRoleId(Integer roleId) {
        Db.update("delete from jfbbs_role_permission where rid = ?", roleId);
    }
}
