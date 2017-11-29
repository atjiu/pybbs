package co.yiiu.module.user.service;

import co.yiiu.module.user.model.User;
import co.yiiu.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "users")
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * search user by score desc
   *
   * @param p
   * @param size
   * @return
   */
  @Cacheable
  public Page<User> findByScore(int p, int size) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "score"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return userRepository.findByBlock(false, pageable);
  }

  @Cacheable
  public User findById(int id) {
    return userRepository.findById(id);
  }

  /**
   * 根据用户名判断是否存在
   *
   * @param username
   * @return
   */
  @Cacheable
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Cacheable
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @CacheEvict(allEntries = true)
  public void save(User user) {
    userRepository.save(user);
  }

  /**
   * 分页查询用户列表
   *
   * @param p
   * @param size
   * @return
   */
  @Cacheable
  public Page<User> pageUser(int p, int size) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return userRepository.findAll(pageable);
  }

  /**
   * 禁用用户
   *
   * @param id
   */
  @CacheEvict(allEntries = true)
  public void blockUser(Integer id) {
    User user = findById(id);
    user.setBlock(true);
    save(user);
  }

  /**
   * 用户解禁
   *
   * @param id
   */
  @CacheEvict(allEntries = true)
  public void unBlockUser(Integer id) {
    User user = findById(id);
    user.setBlock(false);
    save(user);
  }

  /**
   * 根据令牌查询用户
   *
   * @param token
   * @return
   */
  @Cacheable
  public User findByToken(String token) {
    return userRepository.findByToken(token);
  }

  /**
   * 删除用户
   * 注：这会删除用户的所有记录，慎重操作
   * @param id
   */
  //TODO 关联太多，不提供删除用户操作
  //@CacheEvict(allEntries = true)
//    public void deleteById(int id) {
//        User user = findById(id);
//        //删除用户的收藏
//        collectService.deleteByUser(user);
//        //删除用户发的所有回复
//        replyService.deleteByUser(user);
//        //删除用户的通知
//        notificationService.deleteByUser(user);
//        notificationService.deleteByTargetUser(user);
//        //删除该用户的所有话题
//        topicService.deleteByUser(user);
//        //删除用户
//        userDao.deleteById(id);
//    }
}
