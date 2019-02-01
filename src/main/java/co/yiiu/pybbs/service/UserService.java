package co.yiiu.pybbs.service;

import co.yiiu.pybbs.config.service.RedisService;
import co.yiiu.pybbs.mapper.UserMapper;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.Constants;
import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.MyPage;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import co.yiiu.pybbs.util.identicon.Identicon;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
  @Autowired
  private RedisService redisService;

  // 根据用户名查询用户，用于获取用户的信息比对密码
  public User selectByUsername(String username) {
    String userJson = redisService.getString(Constants.REDIS_USER_USERNAME_KEY + username);
    User user;
    if (userJson == null) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.lambda()
          .eq(User::getUsername, username);
      user = userMapper.selectOne(wrapper);
      redisService.setString(Constants.REDIS_USER_USERNAME_KEY + username, JsonUtil.objectToJson(user));
    } else {
      user = JsonUtil.jsonToObject(userJson, User.class);
    }
    return user;
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
  public User addUser(String username, String password, String avatar, String email, String bio, String website) {
    String token = this.generateToken();
    User user = new User();
    user.setUsername(username);
    if (!StringUtils.isEmpty(password)) user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setToken(token);
    user.setInTime(new Date());
    if (avatar == null) avatar = identicon.generator(username);
    user.setAvatar(avatar);
    user.setEmail(email);
    user.setBio(bio);
    user.setWebsite(website);
    userMapper.insert(user);
    // 再查一下，有些数据库里默认值保存后，类里还是null
    return this.selectById(user.getId());
  }

  // 根据用户token查询用户
  public User selectByToken(String token) {
    String userJson = redisService.getString(Constants.REDIS_USER_TOKEN_KEY + token);
    User user;
    if (userJson == null) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.lambda()
          .eq(User::getToken, token);
      user = userMapper.selectOne(wrapper);
      redisService.setString(Constants.REDIS_USER_TOKEN_KEY + token, JsonUtil.objectToJson(user));
    } else {
      user = JsonUtil.jsonToObject(userJson, User.class);
    }
    return user;
  }

  public User selectById(Integer id) {
    String userJson = redisService.getString(Constants.REDIS_USER_ID_KEY + id);
    User user;
    if (userJson == null) {
      user = userMapper.selectById(id);
      redisService.setString(Constants.REDIS_USER_ID_KEY + id, JsonUtil.objectToJson(user));
    } else {
      user = JsonUtil.jsonToObject(userJson, User.class);
    }
    return user;
  }

  // 查询用户积分榜
  public List<User> selectTop(Integer limit) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper
        .orderByDesc("score")
        .last("limit " + limit);
    return userMapper.selectList(wrapper);
  }

  // 更新用户信息
  public void update(User user) {
    userMapper.updateById(user);
    // 删除redis里的缓存
    this.delRedisUser(user);
  }

  // ------------------------------- admin ------------------------------------------

  public IPage<User> selectAll(Integer pageNo) {
    MyPage<User> page = new MyPage<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("page_size")));
    page.setDesc("in_time");
    return userMapper.selectPage(page, null);
  }

  // 查询今天新增的话题数
  public int countToday() {
    return userMapper.countToday();
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
    // 删除redis里的缓存
    User user = this.selectById(id);
    this.delRedisUser(user);
    // 删除用户本身
    userMapper.deleteById(id);
  }

  // 删除redis缓存
  public void delRedisUser(User user) {
    redisService.delString(Constants.REDIS_USER_ID_KEY + user.getId());
    redisService.delString(Constants.REDIS_USER_USERNAME_KEY + user.getUsername());
    redisService.delString(Constants.REDIS_USER_TOKEN_KEY + user.getToken());
  }
}
