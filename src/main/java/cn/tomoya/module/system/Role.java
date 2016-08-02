package cn.tomoya.module.system;

import cn.tomoya.common.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Role extends BaseModel<Role> {

    public static final Role me = new Role();

    public Page<Role> page(Integer pageNumber, Integer pageSize) {
        return super.paginate(pageNumber, pageSize, "select * ", "from pybbs_role");
    }

    /**
     * 根据角色名称查询
     * @param name
     * @return
     */
    public Role findByName(String name) {
        return super.findFirst(
                "select * from pybbs_role where name = ?",
                name
        );
    }

    public List<Role> findAll() {
        return super.find("select * from pybbs_role");
    }

    public void correlationPermission(Integer roleId, Integer[] permissionIds) {
        //先删除已经存在的关联
        Db.update("delete from pybbs_role_permission where rid = ?", roleId);
        //建立新的关联关系
        if(permissionIds != null) {
            for (Integer pid : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.set("rid", roleId)
                        .set("pid", pid)
                        .save();
            }
        }
    }
}
