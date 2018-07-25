package co.yiiu.module.security.service;

import co.yiiu.module.security.mapper.RoleMapper;
import co.yiiu.module.security.pojo.Role;
import co.yiiu.module.security.pojo.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class RoleService {

  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private RolePermissionService rolePermissionService;
  @Autowired
  private AdminUserService adminUserService;

  public Role findById(Integer id) {
    return roleMapper.selectByPrimaryKey(id);
  }

  public List<Role> findAll() {
    return roleMapper.findAll();
  }

  public void saveOrUpdate(Integer id, String name, Integer[] permissionIds) {
    Role role = new Role();
    if(id != null) {
      role = findById(id);
    }
    role.setName(name);
    if (role.getId() == null) {
      roleMapper.insertSelective(role);
    } else {
      roleMapper.updateByPrimaryKeySelective(role);
    }
    // 保存角色权限关联
    rolePermissionService.deleteRoleId(role.getId());
    if(permissionIds.length > 0) {
      List<RolePermission> list = new ArrayList<>();
      for (Integer permissionId : permissionIds) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(role.getId());
        rolePermission.setPermissionId(permissionId);
        list.add(rolePermission);
      }
      rolePermissionService.save(list);
    }
    // 删除redis里的用户缓存
    adminUserService.deleteAllRedisAdminUser();
  }

  public void delete(Integer id) {
    rolePermissionService.deleteRoleId(id);
    roleMapper.deleteByPrimaryKey(id);
    // 删除redis里的用户缓存
    adminUserService.deleteAllRedisAdminUser();
  }

}
