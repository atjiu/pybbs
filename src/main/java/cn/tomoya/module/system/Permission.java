package cn.tomoya.module.system;

import cn.tomoya.common.BaseModel;
import cn.tomoya.common.Constants.CacheEnum;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Permission extends BaseModel<Permission> {

    public static final Permission me = new Permission();

    /**
     * 根据父节点查询权限列表
     * @param pid
     * @return
     */
    public List<Permission> findByPid(Integer pid) {
        return super.find("select * from pybbs_permission where pid = ?", pid);
    }

    /**
     * 查询所有权限（不包括父节点）
     * @return
     */
    public List<Permission> findAll() {
        return super.find("select * from pybbs_permission where pid <> 0");
    }

    /**
     * 查询所有权限，结构是父节点下是子节点的权限列表
     * @return
     */
    public List<Permission> findWithChild() {
        List<Permission> permissions = this.findByPid(0);
        for(Permission p: permissions) {
            p.put("childPermissions", this.findByPid(p.getInt("id")));
        }
        return permissions;
    }

    /**
     * 删除父节点下所有的话题
     * @param pid
     */
    public void deleteByPid(Integer pid) {
        Db.update("delete from pybbs_permission where pid = ?", pid);
    }

    /**
     * 根据用户id查询所拥有的权限
     * @param userId
     * @return
     */
    public Map<String, String> findPermissions(Integer userId) {
        Map<String, String> map = new HashMap<String, String>();
        if(userId == null) return map;
        Cache cache = Redis.use();
        List<Permission> permissions = cache.get(CacheEnum.userpermissions.name() + userId);
        if(permissions == null) {
            permissions = super.find(
                    "select p.* from pybbs_user u, pybbs_role r, pybbs_permission p, " +
                            "pybbs_user_role ur, pybbs_role_permission rp where u.id = ur.uid " +
                            "and r.id = ur.rid and r.id = rp.rid and p.id = rp.pid " +
                            "and u.id = ?",
                    userId
            );
            cache.set(CacheEnum.userpermissions.name() + userId, permissions);
        }
        for (Permission p : permissions) {
            map.put(p.getStr("name"), p.getStr("url"));
        }
        return map;
    }

}
