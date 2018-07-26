package co.yiiu.module.security.mapper;

import co.yiiu.module.security.pojo.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(AdminUser record);

  int insertSelective(AdminUser record);

  AdminUser selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(AdminUser record);

  int updateByPrimaryKey(AdminUser record);

  //自定义方法
  AdminUser findAdminUser(@Param("token") String token, @Param("username") String username);

  List<AdminUser> findAll(
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int count();
}