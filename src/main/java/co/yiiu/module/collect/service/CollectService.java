package co.yiiu.module.collect.service;

import co.yiiu.module.collect.model.Collect;
import co.yiiu.module.collect.repository.CollectRepository;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
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
@CacheConfig(cacheNames = "collects")
public class CollectService {

  @Autowired
  private CollectRepository collectRepository;

  /**
   * 查询用户收藏的话题
   *
   * @param p
   * @param size
   * @param user
   * @return
   */
  @Cacheable
  public Page<Collect> findByUser(int p, int size, User user) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return collectRepository.findByUser(user, pageable);
  }

  /**
   * 查询用户共收藏了多少篇话题
   *
   * @param user
   * @return
   */
  @Cacheable
  public long countByUser(User user) {
    return collectRepository.countByUser(user);
  }

  /**
   * 查询话题共被多少用户收藏
   *
   * @param topic
   * @return
   */
  @Cacheable
  public long countByTopic(Topic topic) {
    return collectRepository.countByTopic(topic);
  }

  /**
   * 根据用户和话题查询收藏记录
   *
   * @param user
   * @param topic
   * @return
   */
  @Cacheable
  public Collect findByUserAndTopic(User user, Topic topic) {
    return collectRepository.findByUserAndTopic(user, topic);
  }

  /**
   * 收藏话题
   *
   * @param collect
   */
  @CacheEvict(allEntries = true)
  public void save(Collect collect) {
    collectRepository.save(collect);
  }

  /**
   * 根据id删除收藏记录
   *
   * @param id
   */
  @CacheEvict(allEntries = true)
  public void deleteById(int id) {
    collectRepository.delete(id);
  }

  /**
   * 用户被删除了，删除对应的所有收藏记录（用户关联关系太多，没法删除用户，所以这个方法也没被调用）
   *
   * @param user
   */
  @CacheEvict(allEntries = true)
  public void deleteByUser(User user) {
    collectRepository.deleteByUser(user);
  }

  /**
   * 话题被删除了，删除对应的所有收藏记录
   * @param topic
   */
  @CacheEvict(allEntries = true)
  public void deleteByTopic(Topic topic) {
    collectRepository.deleteByTopic(topic);
  }
}
