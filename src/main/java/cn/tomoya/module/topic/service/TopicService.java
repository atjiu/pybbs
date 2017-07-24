package cn.tomoya.module.topic.service;

import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.label.service.LabelService;
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

import java.util.Date;
import java.util.List;

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
  @Autowired
  private LabelService labelService;

  /**
   * fuzzy topic by labelId
   * @param labelId
   * @return
   */
  public List<Topic> fuzzyTopicByLabel(int labelId) {
    return topicDao.findByLabelIdLike("%" + labelId + ",%");
  }

  /**
   * save topic
   * @param topic
   */
  public void save(Topic topic) {
    topicDao.save(topic);
  }

  /**
   * query topic by id
   * @param id
   * @return
   */
  public Topic findById(int id) {
    return topicDao.findOne(id);
  }

  /**
   * delete topic by id
   *
   * @param id
   */
  public void deleteById(int id) {
    Topic topic = findById(id);
    if(topic != null) {
      //删除收藏这个话题的记录
      collectService.deleteByTopic(topic);
      //删除通知里提到的话题
      notificationService.deleteByTopic(topic);
      //删除话题下面的回复
      replyService.deleteByTopic(topic);

      // deal label topicCount
      if (!StringUtils.isEmpty(topic.getLabelId())) {
        labelService.dealEditTopicOldLabels(topic.getLabelId());
      }

      //删除话题
      topicDao.delete(topic);
    }
  }

  /**
   * delete topic by user
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
  public Page<Topic> page(int p, int size, String tab, boolean lastest, Integer labelId) {
    Sort sort;
    if(lastest) {
      sort = new Sort(
          new Sort.Order(Sort.Direction.DESC, "inTime"));
    } else {
      sort = new Sort(
          new Sort.Order(Sort.Direction.DESC, "top"),
          new Sort.Order(Sort.Direction.DESC, "inTime"),
          new Sort.Order(Sort.Direction.DESC, "lastReplyTime"));
    }
    Pageable pageable = new PageRequest(p - 1, size, sort);
    if (labelId == null) {
      switch (tab) {
        case "全部":
          return topicDao.findAll(pageable);
        case "精华":
          return topicDao.findByGood(true, pageable);
        case "等待回复":
          return topicDao.findByReplyCount(0, pageable);
        default:
          return topicDao.findByTab(tab, pageable);
      }
    } else {
      return topicDao.findByLabelIdLike("%" + labelId + ",%", pageable);
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

  /**
   * search topic count between date1 and date2
   *
   * @param date1
   * @param date2
   * @return
   */
  public int countByInTimeBetween(Date date1, Date date2) {
    return topicDao.countByInTimeBetween(date1, date2);
  }

  /**
   * search by title to prevent title repeat
   *
   * @param title
   * @return
   */
  public Topic findByTitle(String title) {
    return topicDao.findByTitle(title);
  }
}
