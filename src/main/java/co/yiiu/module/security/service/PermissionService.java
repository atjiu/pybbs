package co.yiiu.module.security.service;

import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.repository.PermissionRepository;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "permissions")
public class PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;
  @Autowired
  private UserService userService;

  public Permission findByName(String name) {
    return permissionRepository.findByName(name);
  }

  /**
   * 根据pid查询权限
   *
   * @param pid
   * @return
   */
  @Cacheable
  public List<Permission> findByPid(int pid) {
    return permissionRepository.findByPid(pid);
  }

  /**
   * 查询权限列表
   *
   * @return
   */
  @Cacheable
  public List findAll(boolean child) {
    if (child) {
      return permissionRepository.findByPidGreaterThan(0);
    } else {
      List list = new ArrayList();
      List<Permission> permissions = this.findByPid(0);
      for (Permission permission : permissions) {
        Map map = new HashMap();
        map.put("permission", permission);
        map.put("childPermissions", this.findByPid(permission.getId()));
        list.add(map);
      }
      return list;
    }
  }

  /**
   * 根据用户的id查询用户的所有权限
   *
   * @param adminUserId
   * @return
   */
  @Cacheable
  public List<Permission> findByAdminUserId(int adminUserId) {
    User user = userService.findById(adminUserId);
    List<Permission> permissions = new ArrayList<>();
    if (user.getRoles().size() > 0) {
      user.getRoles()
          .stream()
          .filter(role -> role.getPermissions().size() > 0)
          .forEach(role -> {
                permissions.addAll(
                    role.getPermissions()
                        .stream()
                        .filter(permission -> permission.getPid() > 0)
                        .collect(Collectors.toList())
                );
              }
          );
    }
    return permissions;
  }

  @CacheEvict(allEntries = true)
  public void save(Permission permission) {
    permissionRepository.save(permission);
  }

  /**
   * 删除权限
   * 判断权限的pid是不是0，是的话，就删除其下所有的权限
   *
   * @param id
   */
  @CacheEvict(allEntries = true)
  public void deleteById(Integer id) {
    Permission permission = findById(id);
    if (permission.getPid() == 0) {
      permissionRepository.deleteByPid(permission.getId());
    }
    permissionRepository.delete(permission);
  }

  @Cacheable
  public Permission findById(int id) {
    return permissionRepository.findById(id);
  }
}
