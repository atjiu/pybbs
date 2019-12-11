package co.yiiu.pybbs.service;

import co.yiiu.pybbs.model.User;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface IUserService {
    // 根据用户名查询用户，用于获取用户的信息比对密码
    User selectByUsername(String username);

    User addUser(String username, String password, String avatar, String email, String bio, String website,
                 boolean needActiveEmail);

    // 通过手机号登录/注册创建用户
    User addUserWithMobile(String mobile);

    // 根据用户token查询用户
    User selectByToken(String token);

    // 根据用户mobile查询用户
    User selectByMobile(String mobile);

    // 根据用户email查询用户
    User selectByEmail(String email);

    User selectById(Integer id);

    // 查询用户积分榜
    List<User> selectTop(Integer limit);

    // 更新用户信息
    void update(User user);

    IPage<User> selectAll(Integer pageNo, String username);

    // 查询今天新增的话题数
    int countToday();

    // 删除用户
    void deleteUser(Integer id);

    // 删除redis缓存
    void delRedisUser(User user);
}
