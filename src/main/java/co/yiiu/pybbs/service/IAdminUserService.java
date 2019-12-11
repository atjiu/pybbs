package co.yiiu.pybbs.service;

import co.yiiu.pybbs.model.AdminUser;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface IAdminUserService {
    // 根据用户名查询用户
    AdminUser selectByUsername(String username);

    // 查询所有的后台用户
    List<Map<String, Object>> selectAll();

    void update(AdminUser adminUser);

    void insert(AdminUser adminUser);

    void delete(Integer id);

    AdminUser selectById(Integer id);

    // 根据角色id查询后台关联的用户
    List<AdminUser> selectByRoleId(Integer roleId);
}
