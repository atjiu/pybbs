package co.yiiu.module.security.service;

import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;
  @Autowired
  private RolePermissionService rolePermissionService;
  @Autowired
  private AdminUserService adminUserService;

  public List<Map<String, Object>> findAll() {
    List<Map<String, Object>> node = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("text", "root");
    map.put("id", 0);
    map.put("pid", 0);
    List<Map<String, Object>> nodes = new ArrayList<>();
    List<Permission> permissions = permissionRepository.findByPid(0);
    permissions.forEach(permission -> {
      Map<String, Object> childMap1 = new HashMap<>();
      childMap1.put("text", permission.getName());
      childMap1.put("pid", permission.getPid());
      childMap1.put("id", permission.getId());
      childMap1.put("value", permission.getValue());
      childMap1.put("url", permission.getUrl());
      if(permission.getPid() == 0) {
        List<Map<String, Object>> childNodes = new ArrayList<>();
        List<Permission> childPermissions = permissionRepository.findByPid(permission.getId());
        childPermissions.forEach(childPermission -> {
          Map<String, Object> childMap2 = new HashMap<>();
          childMap2.put("text", childPermission.getName());
          childMap2.put("pid", childPermission.getPid());
          childMap2.put("id", childPermission.getId());
          childMap2.put("value", childPermission.getValue());
          childMap2.put("url", childPermission.getUrl());
          childNodes.add(childMap2);
        });
        childMap1.put("nodes", childNodes);
      }
      nodes.add(childMap1);
    });
    map.put("nodes", nodes);
    node.add(map);
    return node;
  }

  public Permission save(Permission permission) {
    permission = permissionRepository.save(permission);
    // 删除redis里的用户缓存
    adminUserService.deleteAllRedisAdminUser();
    return permission;
  }

  public List<Permission> findByUserId(Integer userId) {
    return permissionRepository.findByUserId(userId);
  }

  public void delete(Integer id) {
    rolePermissionService.deleteByPermissionId(id);
    permissionRepository.deleteById(id);
    // 删除redis里的用户缓存
    adminUserService.deleteAllRedisAdminUser();
  }

}
