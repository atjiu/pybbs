package cn.tomoya.module.system;

import cn.tomoya.common.BaseModel;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class UserRole extends BaseModel<UserRole> {

    public static final UserRole me = new UserRole();

    public List<UserRole> findByUserId(Integer userId) {
        return super.find("select * from pybbs_user_role where uid = ?", userId);
    }

    public List<UserRole> findByRoleId(Integer roleId) {
        return super.find("select * from pybbs_user_role where rid = ?", roleId);
    }

    public void deleteByUserId(Integer userId) {
        Db.update("delete from pybbs_user_role where uid = ?", userId);
    }

    public void deleteByRoleId(Integer roleId) {
        Db.update("delete from pybbs_user_role where rid = ?", roleId);
    }

}
