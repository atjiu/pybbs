package co.yiiu.module.security.mapper;

import co.yiiu.module.security.pojo.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
  int deleteByPrimaryKey(Long id);

  int insert(RolePermission record);

  int insertSelective(RolePermission record);

  RolePermission selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(RolePermission record);

  int updateByPrimaryKey(RolePermission record);

  //自定义方法
  void deleteRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

  List<RolePermission> findByRoleId(Integer roleId);
}