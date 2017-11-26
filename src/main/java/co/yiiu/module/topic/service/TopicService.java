package co.yiiu.module.topic.service;

import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.node.model.Node;
import co.yiiu.module.notification.service.NotificationService;
import co.yiiu.module.reply.service.ReplyService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.repository.TopicRepository;
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
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "topics")
public class TopicService {

  @Autowired
  private TopicRepository topicRepository;
  @Autowired
  private ReplyService replyService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private NotificationService notificationService;

  /**
   * save topic
   *
   * @param topic
   */
  @CacheEvict(allEntries = true)
  public Topic save(Topic topic) {
    return topicRepository.save(topic);
  }

  /**
   * query topic by id
   *
   * @param id
   * @return
   */
  @Cacheable
  public Topic findById(int id) {
    return topicRepository.findById(id);
  }

  /**
   * delete topic by id
   *
   * @param id
   */
  @CacheEvict(allEntries = true)
  public void deleteById(int id) {
    Topic topic = findById(id);
    if (topic != null) {
      //删除收藏这个话题的记录
      collectService.deleteByTopic(topic);
      //删除通知里提到的话题
      notificationService.deleteByTopic(topic);
      //删除话题下面的回复
      replyService.deleteByTopic(topic);

      //删除话题
      topicRepository.delete(topic);
    }
  }

  /**
   * delete topic by user
   *
   * @param user
   */
  @CacheEvict(allEntries = true)
  public void deleteByUser(User user) {
    topicRepository.deleteByUser(user);
  }

  /**
   * 分页查询话题列表
   *
   * @param p
   * @param size
   * @return
   */
  @Cacheable
  public Page<Topic> page(int p, int size, String tab) {
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.DESC, "top"),
        new Sort.Order(Sort.Direction.DESC, "inTime"),
        new Sort.Order(Sort.Direction.DESC, "lastReplyTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    switch (tab) {
      case "default":
        return topicRepository.findAll(pageable);
      case "good":
        return topicRepository.findByGood(true, pageable);
      case "newest":
        sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
        pageable = new PageRequest(p - 1, size, sort);
        return topicRepository.findAll(pageable);
      case "noanswer":
        return topicRepository.findByReplyCount(0, pageable);
      default:
        return null;
    }
  }

  /**
   * 搜索
   *
   * @param p
   * @param size
   * @param q
   * @return
   */
  @Cacheable
  public Page<Topic> search(int p, int size, String q) {
    if (StringUtils.isEmpty(q)) return null;
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return topicRepository.findByTitleContainingOrContentContaining(q, q, pageable);
  }

  /**
   * 查询用户的话题
   *
   * @param p
   * @param size
   * @param user
   * @return
   */
  @Cacheable
  public Page<Topic> findByUser(int p, int size, User user) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return topicRepository.findByUser(user, pageable);
  }

  /**
   * 查询在date1与date2之前发帖总数
   *
   * @param date1
   * @param date2
   * @return
   */
  @Cacheable
  public int countByInTimeBetween(Date date1, Date date2) {
    return topicRepository.countByInTimeBetween(date1, date2);
  }

  /**
   * 根据标题查询话题（防止发布重复话题）
   *
   * @param title
   * @return
   */
  @Cacheable
  public Topic findByTitle(String title) {
    return topicRepository.findByTitle(title);
  }

  @Cacheable
  public Page<Topic> findByNode(Node node, int p, int size) {
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.DESC, "top"),
        new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return topicRepository.findByNode(node, pageable);
  }

  //查询节点下有多少话题数
  public long countByNode(Node node) {
    return topicRepository.countByNode(node);
  }
}
