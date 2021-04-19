package co.yiiu.pybbs.service.impl;

import co.yiiu.pybbs.mapper.PermissionMapper;
import co.yiiu.pybbs.model.Permission;
import co.yiiu.pybbs.model.RolePermission;
import co.yiiu.pybbs.service.IPermissionService;
import co.yiiu.pybbs.service.IRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class PermissionService implements IPermissionService {

    private static Map<String, List<Permission>> permissionsByRoleId = new HashMap<>();

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private IRolePermissionService rolePermissionService;

    public void clearRolePermissionCache() {
        permissionsByRoleId.clear();
    }

    // 根据角色id查询所有的权限, 这个方法调用非常频繁，在内存里缓存一下
    @Override
    public List<Permission> selectByRoleId(Integer roleId) {
        if (permissionsByRoleId.get("roleId_" + roleId) != null) return permissionsByRoleId.get("roleId_" + roleId);
        List<RolePermission> rolePermissions = rolePermissionService.selectByRoleId(roleId);
        List<Integer> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Permission::getId, permissionIds);
        List<Permission> permissions = permissionMapper.selectList(wrapper);
        permissionsByRoleId.put("roleId_" + roleId, permissions);
        return permissions;
    }

    // 根据父节点查询子节点
    @Override
    public List<Permission> selectByPid(Integer pid) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Permission::getPid, pid);
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public Map<String, List<Permission>> selectAll() {
        Map<String, List<Permission>> map = new LinkedHashMap<>();
        // 先查父节点
        List<Permission> permissions = this.selectByPid(0);
        // 再查子节点
        permissions.forEach(permission -> map.put(permission.getName(), this.selectByPid(permission.getId())));
        return map;
    }

    @Override
    public Permission insert(Permission permission) {
        this.clearPermissionsCache();
        permissionMapper.insert(permission);
        return permission;
    }

    @Override
    public Permission update(Permission permission) {
        this.clearPermissionsCache();
        permissionMapper.updateById(permission);
        return permission;
    }

    @Override
    public void delete(Integer id) {
        this.clearPermissionsCache();
        Permission permission = permissionMapper.selectById(id);
        // 如果是父节点的话，要把所有子节点下的所有关联角色的记录都删了，否则会报错
        if (permission.getPid() == 0) {
            List<Permission> permissions = this.selectByPid(permission.getId());
            permissions.forEach(permission1 -> {
                // 先删除role_permission里的关联数据
                rolePermissionService.deleteByPermissionId(permission1.getId());
                // 删除子节点
                permissionMapper.deleteById(permission1.getId());
            });
        } else {
            // 先删除role_permission里的关联数据
            rolePermissionService.deleteByPermissionId(id);
        }
        // 删除自己
        permissionMapper.deleteById(id);
    }

    private void clearPermissionsCache() {
        permissionsByRoleId = new HashMap<>();
    }
}
