package cn.tomoya.module.user.service;

import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.dao.UserDao;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
     * according to the user name to determine whether where is
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.save(user);
    }

    /**
     * paging query user list
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
     * disable the user
     * @param id
     */
    public void blockUser(Integer id) {
        User user = findById(id);
        user.setBlock(true);
        updateUser(user);
    }

    /**
     * remove the disabled of user
     * @param id
     */
    public void unBlockUser(Integer id) {
        User user = findById(id);
        user.setBlock(false);
        updateUser(user);
    }

    /**
     * delete user
     * notice: this operation will delete all data of user, careful operation
     * @param id
     */
    //TODO too many association, do not provide delete user actions
//    public void deleteById(int id) {
//        User user = findById(id);
//        // delete user collection
//        collectService.deleteByUser(user);
//        // delete all comments of user
//        replyService.deleteByUser(user);
//        // delete user notification
//        notificationService.deleteByUser(user);
//        notificationService.deleteByTargetUser(user);
//        // delete all topics of user
//        topicService.deleteByUser(user);
//        // delete user
//        userDao.delete(user);
//    }

    @CacheEvict("users")
    public void clearCache() {}
}
