package co.yiiu.module.security.mapper;

import co.yiiu.module.security.pojo.Role;

import java.util.List;

public interface RoleMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(Role record);

  int insertSelective(Role record);

  Role selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Role record);

  int updateByPrimaryKey(Role record);

  //自定义方法
  List<Role> findAll();
}