package cn.tomoya.module.topic.service;

import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.dao.TopicDao;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class TopicService {

  @Autowired
  private TopicDao topicDao;
  @Autowired
  private ReplyService replyService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private NotificationService notificationService;

  public void save(Topic topic) {
    topicDao.save(topic);
  }

  public Topic findById(int id) {
    return topicDao.findOne(id);
  }

  /**
   * 删除话题
   *
   * @param id
   */
  public void deleteById(int id) {
    Topic topic = findById(id);
    //删除收藏这个话题的记录
    collectService.deleteByTopic(topic);
    //删除通知里提到的话题
    notificationService.deleteByTopic(topic);
    //删除话题下面的回复
    replyService.deleteByTopic(topic);
    //删除话题
    topicDao.delete(topic);
  }

  /**
   * 删除用户发的所有话题
   *
   * @param user
   */
  public void deleteByUser(User user) {
    topicDao.deleteByUser(user);
  }

  /**
   * 分页查询话题列表
   *
   * @param p
   * @param size
   * @return
   */
  public Page<Topic> page(int p, int size, String tab) {
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.DESC, "top"),
        new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    if (tab.equals("全部")) {
      return topicDao.findAll(pageable);
    } else if (tab.equals("精华")) {
      return topicDao.findByGood(true, pageable);
    } else if (tab.equals("等待回复")) {
      return topicDao.findByReplyCount(0, pageable);
    } else {
      return topicDao.findByTab(tab, pageable);
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
  public Page<Topic> search(int p, int size, String q) {
    if (StringUtils.isEmpty(q)) return null;
    Sort sort = new Sort(
        new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return topicDao.findByTitleContainingOrContentContaining(q, q, pageable);
  }

  /**
   * 增加回复数
   *
   * @param topicId
   */
  public void addOneReplyCount(int topicId) {
    Topic topic = findById(topicId);
    if (topic != null) {
      topic.setReplyCount(topic.getReplyCount() + 1);
      save(topic);
    }
  }

  /**
   * 减少回复数
   *
   * @param topicId
   */
  public void reduceOneReplyCount(int topicId) {
    Topic topic = findById(topicId);
    if (topic != null) {
      topic.setReplyCount(topic.getReplyCount() - 1);
      save(topic);
    }
  }

  /**
   * 查询用户的话题
   *
   * @param p
   * @param size
   * @param user
   * @return
   */
  public Page<Topic> findByUser(int p, int size, User user) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return topicDao.findByUser(user, pageable);
  }
}
