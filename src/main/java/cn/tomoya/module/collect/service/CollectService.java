package cn.tomoya.module.collect.service;

import cn.tomoya.module.collect.dao.CollectDao;
import cn.tomoya.module.collect.entity.Collect;
import cn.tomoya.module.topic.entity.Topic;
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
public class CollectService {

    @Autowired
    private CollectDao collectDao;

    /**
     * query user collection topics
     *
     * @param p
     * @param size
     * @param user
     * @return
     */
    public Page<Collect> findByUser(int p, int size, User user) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return collectDao.findByUser(user, pageable);
    }

    /**
     * query the user a total of how many topic collection
     *
     * @param user
     * @return
     */
    public long countByUser(User user) {
        return collectDao.countByUser(user);
    }

    /**
     * how many users are collected
     *
     * @param topic
     * @return
     */
    public long countByTopic(Topic topic) {
        return collectDao.countByTopic(topic);
    }

    /**
     * query collections by user and topic
     *
     * @param user
     * @param topic
     * @return
     */
    public Collect findByUserAndTopic(User user, Topic topic) {
        return collectDao.findByUserAndTopic(user, topic);
    }

    /**
     * collection topic
     *
     * @param collect
     */
    public void save(Collect collect) {
        collectDao.save(collect);
    }

    /**
     * delete collection
     *
     * @param id
     */
    public void deleteById(int id) {
        collectDao.delete(id);
    }

    /**
     * delete user's collection
     *
     * @param user
     */
    public void deleteByUser(User user) {
        collectDao.deleteByUser(user);
    }
}
