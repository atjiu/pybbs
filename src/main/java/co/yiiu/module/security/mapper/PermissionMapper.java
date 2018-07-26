package co.yiiu.module.security.mapper;

import co.yiiu.module.security.pojo.Permission;

import java.util.List;

public interface PermissionMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(Permission record);

  int insertSelective(Permission record);

  Permission selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Permission record);

  int updateByPrimaryKey(Permission record);

  //自定义方法
  List<Permission> findByUserId(Integer userId);

  List<Permission> findByPid(Integer pid);

  List<Permission> findByPidGreaterThan(Integer pid);

}