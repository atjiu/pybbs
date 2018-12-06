package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.RolePermissionMapper;
import co.yiiu.pybbs.model.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class RolePermissionService {

  @Autowired
  private RolePermissionMapper rolePermissionMapper;

  // 根据角色id查询所有的角色权限关联记录
  public List<RolePermission> selectByRoleId(Integer roleId) {
    QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(RolePermission::getRoleId, roleId);
    return rolePermissionMapper.selectList(wrapper);
  }

  // 根据角色id删除关联关系
  public void deleteByRoleId(Integer roleId) {
    QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(RolePermission::getRoleId, roleId);
    rolePermissionMapper.delete(wrapper);
  }

  // 根据权限id删除关联关系
  public void deleteByPermissionId(Integer permissionId) {
    QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(RolePermission::getPermissionId, permissionId);
    rolePermissionMapper.delete(wrapper);
  }

  public void insert(RolePermission rolePermission) {
    rolePermissionMapper.insert(rolePermission);
  }

}
