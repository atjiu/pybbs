package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.AdminUserMapper;
import co.yiiu.pybbs.model.AdminUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class AdminUserService {

  @Autowired
  private AdminUserMapper adminUserMapper;

  // 根据用户名查询用户
  public AdminUser selectByUsername(String username) {
    QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(AdminUser::getUsername, username);
    return adminUserMapper.selectOne(wrapper);
  }

  // 查询所有的后台用户
  public List<Map<String, Object>> selectAll() {
    return adminUserMapper.selectAll();
  }

  public void update(AdminUser adminUser) {
    adminUserMapper.updateById(adminUser);
  }

  public void insert(AdminUser adminUser) {
    adminUserMapper.insert(adminUser);
  }

  public void delete(Integer id) {
    adminUserMapper.deleteById(id);
  }

  public AdminUser selectById(Integer id) {
    return adminUserMapper.selectById(id);
  }

  // 根据角色id查询后台关联的用户
  public List<AdminUser> selectByRoleId(Integer roleId) {
    QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(AdminUser::getRoleId, roleId);
    return adminUserMapper.selectList(wrapper);
  }
}
