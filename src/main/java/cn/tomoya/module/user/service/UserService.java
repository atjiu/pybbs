package cn.tomoya.module.user.service;

import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.dao.UserDao;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class UserService {

  @Autowired
  private UserDao userDao;
  @Autowired
  private TopicService topicService;
  @Autowired
  private ReplyService replyService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private CollectService collectService;

  public User findById(int id) {
    return userDao.findOne(id);
  }

  /**
   * 根据用户名判断是否存在
   *
   * @param username
   * @return
   */
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  public User findByEmail(String email) {
    return userDao.findByEmail(email);
  }

  public void save(User user) {
    userDao.save(user);
  }

  public void updateUser(User user) {
    userDao.save(user);
  }

  /**
   * 分页查询用户列表
   *
   * @param p
   * @param size
   * @return
   */
  public Page<User> pageUser(int p, int size) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return userDao.findAll(pageable);
  }

  /**
   * 禁用用户
   *
   * @param id
   */
  public void blockUser(Integer id) {
    User user = findById(id);
    user.setBlock(true);
    updateUser(user);
  }

  /**
   * 用户解禁
   *
   * @param id
   */
  public void unBlockUser(Integer id) {
    User user = findById(id);
    user.setBlock(false);
    updateUser(user);
  }

  /**
   * 根据令牌查询用户
   *
   * @param token
   * @return
   */
  public User findByToken(String token) {
    return userDao.findByToken(token);
  }

  /**
   * 删除用户
   * 注：这会删除用户的所有记录，慎重操作
   * @param id
   */
  //TODO 关联太多，不提供删除用户操作
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
//        userDao.delete(user);
//    }
}
