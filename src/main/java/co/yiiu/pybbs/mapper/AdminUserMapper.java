package co.yiiu.pybbs.mapper;

import co.yiiu.pybbs.model.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

  @Select("select u.*, r.name as roleName from admin_user u left join role r on u.role_id = r.id")
  List<Map<String, Object>> selectAll();
}
