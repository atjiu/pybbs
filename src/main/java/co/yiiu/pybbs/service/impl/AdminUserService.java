package co.yiiu.pybbs.service.impl;

import co.yiiu.pybbs.mapper.AdminUserMapper;
import co.yiiu.pybbs.model.AdminUser;
import co.yiiu.pybbs.service.IAdminUserService;
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
public class AdminUserService implements IAdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    // 根据用户名查询用户
    @Override
    public AdminUser selectByUsername(String username) {
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AdminUser::getUsername, username);
        return adminUserMapper.selectOne(wrapper);
    }

    // 查询所有的后台用户
    @Override
    public List<Map<String, Object>> selectAll() {
        return adminUserMapper.selectAll();
    }

    @Override
    public void update(AdminUser adminUser) {
        adminUserMapper.updateById(adminUser);
    }

    @Override
    public void insert(AdminUser adminUser) {
        adminUserMapper.insert(adminUser);
    }

    @Override
    public void delete(Integer id) {
        adminUserMapper.deleteById(id);
    }

    @Override
    public AdminUser selectById(Integer id) {
        return adminUserMapper.selectById(id);
    }

    // 根据角色id查询后台关联的用户
    @Override
    public List<AdminUser> selectByRoleId(Integer roleId) {
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AdminUser::getRoleId, roleId);
        return adminUserMapper.selectList(wrapper);
    }
}
