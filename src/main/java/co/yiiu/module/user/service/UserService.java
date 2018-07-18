package co.yiiu.module.user.service;

import co.yiiu.core.util.JsonUtil;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.comment.service.CommentService;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.notification.service.NotificationService;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LogService logService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public User createUser(String username, String password, String email, String avatar, String url, String bio) {
    if(!StringUtils.isEmpty(email) && email.equals("null")) email = null;
    User user = new User();
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(new BCryptPasswordEncoder().encode(password));
    user.setInTime(new Date());
    user.setBlock(false);
    user.setToken(UUID.randomUUID().toString());
    user.setAvatar(avatar);
    user.setUrl(url);
    user.setBio(bio);
    user.setReputation(0);
    // 默认邮件打开
    user.setCommentEmail(true);
    user.setReplyEmail(true);
    return this.save(user);
  }

  /**
   * search user by log desc
   *
   * @param p
   * @param size
   * @return
   */
  public Page<User> findByReputation(int p, int size) {
    Sort sort = new Sort(Sort.Direction.DESC, "reputation");
    Pageable pageable = PageRequest.of(p - 1, size, sort);
    return userRepository.findAll(pageable);
  }

  public User findById(int id) {
    return userRepository.findById(id);
  }

  /**
   * 根据用户名判断是否存在
   *
   * @param username
   * @return
   */
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User save(User user) {
    user = userRepository.save(user);
    this.refreshCache(user);
    return user;
  }

  /**
   * 分页查询用户列表
   *
   * @param p
   * @param size
   * @return
   */
  public Page<User> pageUser(int p, int size) {
    Sort sort = new Sort(Sort.Direction.DESC, "inTime");
    Pageable pageable = PageRequest.of(p - 1, size, sort);
    return userRepository.findAll(pageable);
  }

  /**
   * 禁用/解禁用户
   *
   * @param id
   */
  public void blockUser(Integer id) {
    User user = findById(id);
    user.setBlock(!user.getBlock());
    save(user);
    this.refreshCache(user);
  }

  public User refreshToken(User user) {
    user.setToken(UUID.randomUUID().toString());
    return this.save(user);
  }

  /**
   * 根据令牌查询用户
   *
   * @param token
   * @return
   */
  public User findByToken(String token) {
    return userRepository.findByToken(token);
  }

  public void deleteById(Integer id) {
    // 删除用户的日志
    logService.deleteByUserId(id);
    // 删除用户的通知
    notificationService.deleteByTargetUser(id);
    // 删除用户的收藏
    collectService.deleteByUserId(id);
    // 删除用户的评论
    commentService.deleteByUserId(id);
    // 删除用户的话题
    topicService.deleteByUserId(id);
    // 删除用户
    userRepository.deleteById(id);
  }

  // 删除所有后台用户存在redis里的数据
  public void deleteAllRedisUser() {
    List<User> users = userRepository.findAll();
    users.forEach(user -> stringRedisTemplate.delete(user.getToken()));
  }

  public void refreshCache(User user) {
    // 更新redis里的数据
    ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
    stringStringValueOperations.set(user.getToken(), JsonUtil.objectToJson(user));
  }
}
