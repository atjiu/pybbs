package co.yiiu.module.security.service;

import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.repository.PermissionRepository;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;
  @Autowired
  private UserService userService;

  /**
   * 查询所有的权限
   *
   * @return
   */
  public List<Permission> findAllChildPermission() {
    return permissionRepository.findByPidGreaterThan(0);
  }

  /**
   * 根据pid查询权限
   *
   * @param pid
   * @return
   */
  public List<Permission> findByPid(int pid) {
    return permissionRepository.findByPid(pid);
  }

  /**
   * 查询权限列表
   *
   * @return
   */
  public List findAll() {
    List list = new ArrayList();
    Map map;
    List<Permission> permissions = permissionRepository.findByPid(0);
    for (Permission permission : permissions) {
      map = new HashMap();
      map.put("permission", permission);
      map.put("childPermissions", permissionRepository.findByPid(permission.getId()));
      list.add(map);
    }
    return list;
  }

  /**
   * 根据用户的id查询用户的所有权限
   *
   * @param adminUserId
   * @return
   */
  public List<Permission> findByAdminUserId(int adminUserId) {
    User user = userService.findById(adminUserId);
    List<Permission> permissions = new ArrayList<>();
    if (user.getRoles().size() > 0) {
      user.getRoles().stream().filter(role -> role.getPermissions().size() > 0).forEach(role -> {
        permissions.addAll(role.getPermissions().stream().filter(permission -> permission.getPid() > 0).collect(Collectors.toList()));
      });
    }
    return permissions;
  }

  public void save(Permission permission) {
    permissionRepository.save(permission);
  }

  /**
   * 删除权限
   * 判断权限的pid是不是0，是的话，就删除其下所有的权限
   *
   * @param id
   */
  public void deleteById(Integer id) {
    Permission permission = findById(id);
    if (permission.getPid() == 0) {
      permissionRepository.deleteByPid(permission.getId());
    }
    permissionRepository.delete(permission);
  }

  public Permission findById(int id) {
    return permissionRepository.findById(id);
  }
}
