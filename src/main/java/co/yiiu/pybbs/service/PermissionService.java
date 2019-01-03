package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.PermissionMapper;
import co.yiiu.pybbs.model.Permission;
import co.yiiu.pybbs.model.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PermissionService {

  @Autowired
  private PermissionMapper permissionMapper;
  @Autowired
  private RolePermissionService rolePermissionService;

  // 根据角色id查询所有的权限
  public List<Permission> selectByRoleId(Integer roleId) {
    List<RolePermission> rolePermissions = rolePermissionService.selectByRoleId(roleId);
    List<Integer> permissionIds = rolePermissions
        .stream()
        .map(RolePermission::getPermissionId)
        .collect(Collectors.toList());
    QueryWrapper<Permission> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .in(Permission::getId, permissionIds);
    return permissionMapper.selectList(wrapper);
  }

  // 根据父节点查询子节点
  public List<Permission> selectByPid(Integer pid) {
    QueryWrapper<Permission> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(Permission::getPid, pid);
    return permissionMapper.selectList(wrapper);
  }

  public Map<String, List<Permission>> selectAll() {
    Map<String, List<Permission>> map = new LinkedHashMap<>();
    // 先查父节点
    List<Permission> permissions = this.selectByPid(0);
    // 再查子节点
    permissions.forEach(permission -> map.put(permission.getName(), this.selectByPid(permission.getId())));
    return map;
  }

  public Permission insert(Permission permission) {
    permissionMapper.insert(permission);
    return permission;
  }

  public Permission update(Permission permission) {
    permissionMapper.updateById(permission);
    return permission;
  }

  public void delete(Integer id) {
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
}
