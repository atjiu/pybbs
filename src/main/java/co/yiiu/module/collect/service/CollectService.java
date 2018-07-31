package co.yiiu.module.collect.service;

import co.yiiu.core.bean.Page;
import co.yiiu.core.util.JsonUtil;
import co.yiiu.module.collect.mapper.CollectMapper;
import co.yiiu.module.collect.pojo.Collect;
import co.yiiu.module.log.pojo.LogEventEnum;
import co.yiiu.module.log.pojo.LogTargetEnum;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.notification.pojo.NotificationEnum;
import co.yiiu.module.notification.service.NotificationService;
import co.yiiu.module.topic.pojo.Topic;
import co.yiiu.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class CollectService {

  @Autowired
  private CollectMapper collectMapper;
  @Autowired
  private TopicService topicService;
  @Autowired
  private LogService logService;
  @Autowired
  private NotificationService notificationService;

  public Page<Map> findByUserId(Integer pageNo, Integer pageSize, Integer userId) {
    List<Map> list = collectMapper.findByUserId(userId, (pageNo - 1) * pageSize, pageSize, "t.id desc");
    int count = collectMapper.countByUserId(userId);
    return new Page<>(pageNo, pageSize, count, list);
  }

  public long countByUserId(Integer userId) {
    return collectMapper.countByUserId(userId);
  }

  public long countByTopicId(Integer topicId) {
    return collectMapper.countByTopicId(topicId);
  }

  public Collect findByUserIdAndTopicId(Integer userId, Integer topicId) {
    return collectMapper.findByUserIdAndTopicId(userId, topicId);
  }

  public void save(Collect collect) {
    collectMapper.insertSelective(collect);
  }

  public Collect createCollect(Topic topic, Integer userId) {
    Collect collect = new Collect();
    collect.setInTime(new Date());
    collect.setTopicId(topic.getId());
    collect.setUserId(userId);
    this.save(collect);
    // 通知
    if (!topic.getUserId().equals(userId)) {
      notificationService.sendNotification(userId, topic.getUserId(), NotificationEnum.COLLECT, topic.getId(), null);
    }
    // 日志
    logService.save(LogEventEnum.COLLECT_TOPIC, collect.getUserId(), LogTargetEnum.COLLECT.name(), collect.getId(), null, JsonUtil.objectToJson(collect), topic);
    return collect;
  }

  public void deleteById(int id) {
    Collect collect = collectMapper.selectByPrimaryKey(id);
    // 日志
    Topic topic = topicService.findById(collect.getTopicId());
    logService.save(LogEventEnum.DELETE_COLLECT_TOPIC, collect.getUserId(), LogTargetEnum.COLLECT.name(), collect.getId(), JsonUtil.objectToJson(collect), null, topic);
    collectMapper.deleteByPrimaryKey(id);
  }

  /**
   * 用户被删除了，删除对应的所有收藏记录
   * 不做日志，原因 {@link TopicService#deleteByUserId(Integer)}
   *
   * @param userId
   */
  public void deleteByUserId(Integer userId) {
    collectMapper.deleteByUserId(userId);
  }

  /**
   * 话题被删除了，删除对应的所有收藏记录
   *
   * @param topicId
   */
  public void deleteByTopicId(Integer topicId) {
    collectMapper.deleteByTopicId(topicId);
  }
}
