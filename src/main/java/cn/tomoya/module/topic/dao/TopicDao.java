package cn.tomoya.module.topic.dao;

import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Repository
@CacheConfig(cacheNames = "topics")
public interface TopicDao extends JpaRepository<Topic, Integer> {

  @Cacheable
  Topic findById(int id);

  @Cacheable
  Page<Topic> findByTab(String tab, Pageable pageable);

  @Cacheable
  Page<Topic> findByUser(User user, Pageable pageable);

  void deleteByUser(User user);

  @Cacheable
  Page<Topic> findByGood(boolean b, Pageable pageable);

  @Cacheable
  Page<Topic> findByReplyCount(int i, Pageable pageable);

  Page<Topic> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

  int countByInTimeBetween(Date date1, Date date2);

  Topic findByTitle(String title);

  List<Topic> findByLabelIdLike(String labelId);

  @Cacheable
  Page<Topic> findByLabelIdLike(String labelId, Pageable pageable);

  @CacheEvict
  void delete(Topic topic);
}
