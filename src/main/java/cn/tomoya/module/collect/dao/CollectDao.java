package cn.tomoya.module.collect.dao;

import cn.tomoya.module.collect.entity.Collect;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Repository
public interface CollectDao extends JpaRepository<Collect, Integer> {

    Page<Collect> findByUser(User user, Pageable pageable);

    long countByUser(User user);

    long countByTopic(Topic topic);

    Collect findByUserAndTopic(User user, Topic topic);

}
