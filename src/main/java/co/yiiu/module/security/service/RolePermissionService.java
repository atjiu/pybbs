package co.yiiu.module.security.service;

import co.yiiu.module.security.mapper.RolePermissionMapper;
import co.yiiu.module.security.pojo.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class RolePermissionService {

  @Autowired
  private RolePermissionMapper rolePermissionMapper;

  public void save(RolePermission rolePermission) {
    rolePermissionMapper.insert(rolePermission);
  }

  public void save(List<RolePermission> rolePermissions) {
    for (RolePermission rolePermission : rolePermissions) {
      this.save(rolePermission);
    }
  }

  public void deleteRoleId(Integer roleId) {
    rolePermissionMapper.deleteRolePermission(roleId, null);
  }

  public List<RolePermission> findByRoleId(Integer roleId) {
    return rolePermissionMapper.findByRoleId(roleId);
  }

  public void deleteByPermissionId(Integer permissionId) {
    rolePermissionMapper.deleteRolePermission(null, permissionId);
  }
}
