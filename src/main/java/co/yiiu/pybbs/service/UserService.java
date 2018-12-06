package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.UserMapper;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import co.yiiu.pybbs.util.identicon.Identicon;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class UserService {

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private CollectService collectService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private Identicon identicon;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private SystemConfigService systemConfigService;

  // 根据用户名查询用户，用于获取用户的信息比对密码
  public User selectByUsername(String username) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(User::getUsername, username);
    return userMapper.selectOne(wrapper);
  }

  // 递归生成token，防止token重复
  // 理论上uuid生成的token是不可能重复的
  // 加个逻辑放心 : )
  private String generateToken() {
    String token = UUID.randomUUID().toString();
    User user = this.selectByToken(token);
    if (user != null) {
      return this.generateToken();
    }
    return token;
  }

  // 注册创建用户
  public User addUser(String username, String password) {
    String token = this.generateToken();
    User user = new User();
    user.setUsername(username);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setToken(token);
    user.setInTime(new Date());
    user.setAvatar(identicon.generator(username));
    userMapper.insert(user);
    // 再查一下，有些数据库里默认值保存后，类里还是null
    return this.selectById(user.getId());
  }

  // 根据用户token查询用户
  public User selectByToken(String token) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(User::getToken, token);
    return userMapper.selectOne(wrapper);
  }

  public User selectById(Integer id) {
    return userMapper.selectById(id);
  }

  // 查询用户积分榜
  public List<User> selectTop(Integer limit) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper
        .orderByDesc("score")
        .last("limit " + limit);
    return userMapper.selectList(wrapper);
  }

  public void update(User user) {
    userMapper.updateById(user);
  }

  // ------------------------------- admin ------------------------------------------

  public IPage<User> selectAll(Integer pageNo) {
    Page<User> page = new Page<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("pageSize")));
    page.setDesc("in_time");
    return userMapper.selectPage(page, null);
  }

  // 删除用户
  public void deleteUser(Integer id) {
    // 删除用户的通知
    notificationService.deleteByUserId(id);
    // 删除用户的收藏
    collectService.deleteByUserId(id);
    // 删除用户发的帖子
    topicService.deleteByUserId(id);
    // 删除用户发的评论
    commentService.deleteByUserId(id);
    // 删除用户本身
    userMapper.deleteById(id);
  }
}
