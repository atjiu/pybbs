package cn.tomoya.module.system;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
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
        return super.findFirstByCache(
                Constants.ROLE_CACHE,
                Constants.ROLE_CACHE_KEY + "_role_" + name,
                "select * from pybbs_role where name = ?",
                name
        );
    }

    public List<Role> findAll() {
        return super.find("select * from pybbs_role");
    }

    public Set<String> findRoles(String username) {
        List<Role> roles = super.findByCache(
                Constants.ROLE_CACHE,
                Constants.ROLE_CACHE_KEY + username,
                "select r.* from pybbs_admin_user u, pybbs_role r, pybbs_user_role ur " +
                        "where u.id = ur.uid and r.id = ur.rid and u.username = ?",
                username
        );
        Set<String> set = new HashSet<String>();
        for (Role r : roles) {
            set.add(r.getStr("name"));
        }
        return set;
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
