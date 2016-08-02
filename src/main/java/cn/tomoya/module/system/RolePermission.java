package cn.tomoya.module.system;

import cn.tomoya.common.BaseModel;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class RolePermission extends BaseModel<RolePermission> {

    public static final RolePermission me = new RolePermission();

    public List<RolePermission> findByRoleId(Integer roleId) {
        return super.find("select * from pybbs_role_permission where rid = ?", roleId);
    }

    public void deleteByPermissionId(Integer permissionId) {
        Db.update("delete from pybbs_role_permission where pid = ?", permissionId);
    }

    public void deleteByRoleId(Integer roleId) {
        Db.update("delete from pybbs_role_permission where rid = ?", roleId);
    }
}
