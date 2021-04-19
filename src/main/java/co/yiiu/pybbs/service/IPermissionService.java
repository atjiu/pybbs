package co.yiiu.pybbs.service;

import co.yiiu.pybbs.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface IPermissionService {

    // 更新角色关联的权限时调用一下，清除缓存，让权限实时生效
    void clearRolePermissionCache();

    // 根据角色id查询所有的权限
    List<Permission> selectByRoleId(Integer roleId);

    // 根据父节点查询子节点
    List<Permission> selectByPid(Integer pid);

    Map<String, List<Permission>> selectAll();

    Permission insert(Permission permission);

    Permission update(Permission permission);

    void delete(Integer id);
}
