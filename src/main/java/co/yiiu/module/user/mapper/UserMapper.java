package co.yiiu.module.user.mapper;

import co.yiiu.module.user.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(User record);

  int insertSelective(User record);

  User selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(User record);

  int updateByPrimaryKey(User record);

  // 自定义方法
  List<User> findAll(
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int count();

  User findUser(
      @Param("token") String token,
      @Param("username") String username,
      @Param("email") String email
  );
}