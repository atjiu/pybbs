package co.yiiu.module.reply.service;

import co.yiiu.core.util.Constants;
import co.yiiu.module.reply.model.Reply;
import co.yiiu.module.reply.repository.ReplyRepository;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "replies")
public class ReplyService {

  @Autowired
  private ReplyRepository replyRepository;
  @Autowired
  private TopicService topicService;

  /**
   * 根据id查询评论
   * @param id
   * @return
   */
  @Cacheable
  public Reply findById(int id) {
    return replyRepository.findOne(id);
  }

  /**
   * 保存评论
   * @param reply
   */
  @CacheEvict(allEntries = true)
  public void save(Reply reply) {
    replyRepository.save(reply);
  }

  /**
   * 根据id删除评论
   * @param id
   * @return
   */
  @CacheEvict(allEntries = true)
  public Map delete(int id) {
    Map map = new HashMap();
    Reply reply = findById(id);
    if (reply != null) {
      map.put("topicId", reply.getTopic().getId());
      Topic topic = reply.getTopic();
      topic.setReplyCount(topic.getReplyCount() - 1);
      topicService.save(topic);
      replyRepository.delete(id);
    }
    return map;
  }

  /**
   * 删除用户发布的所有回复
   *
   * @param user
   */
  @CacheEvict(allEntries = true)
  public void deleteByUser(User user) {
    replyRepository.deleteByUser(user);
  }

  /**
   * 根据话题删除回复
   *
   * @param topic
   */
  @CacheEvict(allEntries = true)
  public void deleteByTopic(Topic topic) {
    replyRepository.deleteByTopic(topic);
  }

  /**
   * 赞
   *
   * @param userId
   * @param reply
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Reply up(int userId, Reply reply) {
    String upIds = reply.getUpIds();
    if (upIds == null) upIds = Constants.COMMA;
    if (!upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
      reply.setUp(reply.getUp() + 1);
      reply.setUpIds(upIds + userId + Constants.COMMA);

      String downIds = reply.getDownIds();
      if (downIds == null) downIds = Constants.COMMA;
      if (downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        reply.setDown(reply.getDown() - 1);
        downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        reply.setDownIds(downIds);
      }
      int count = reply.getUp() - reply.getDown();
      reply.setUpDown(count > 0 ? count : 0);
      save(reply);
    }
    return reply;
  }

  /**
   * 取消赞
   *
   * @param userId
   * @param replyId
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Reply cancelUp(int userId, int replyId) {
    Reply reply = findById(replyId);
    if (reply != null) {
      String upIds = reply.getUpIds();
      if (upIds == null) upIds = Constants.COMMA;
      if (upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        reply.setUp(reply.getUp() - 1);
        upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        reply.setUpIds(upIds);

        int count = reply.getUp() - reply.getDown();
        reply.setUpDown(count > 0 ? count : 0);
        save(reply);
      }
    }
    return reply;
  }

  /**
   * 踩
   *
   * @param userId
   * @param reply
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Reply down(int userId, Reply reply) {
    String downIds = reply.getDownIds();
    if (downIds == null) downIds = Constants.COMMA;
    if (!downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
      reply.setDown(reply.getDown() + 1);
      reply.setDownIds(downIds + userId + Constants.COMMA);

      String upIds = reply.getUpIds();
      if (upIds == null) upIds = Constants.COMMA;
      if (upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        reply.setUp(reply.getUp() - 1);
        upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        reply.setUpIds(upIds);
      }
      int count = reply.getUp() - reply.getDown();
      reply.setUpDown(count > 0 ? count : 0);
      save(reply);
    }
    return reply;
  }

  /**
   * 取消踩
   *
   * @param userId
   * @param replyId
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Reply cancelDown(int userId, int replyId) {
    Reply reply = findById(replyId);
    if (reply != null) {
      String downIds = reply.getDownIds();
      if (downIds == null) downIds = Constants.COMMA;
      if (downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        reply.setDown(reply.getDown() - 1);
        downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        reply.setDownIds(downIds);

        int count = reply.getUp() - reply.getDown();
        reply.setUpDown(count > 0 ? count : 0);
        save(reply);
      }
    }
    return reply;
  }

  /**
   * 根据话题查询回复列表
   *
   * @param topic
   * @return
   */
  @Cacheable
  public List<Reply> findByTopic(Topic topic) {
    return replyRepository.findByTopicOrderByUpDownDescDownAscInTimeAsc(topic);
  }

  /**
   * 分页查询回复列表
   *
   * @param p
   * @param size
   * @return
   */
  @Cacheable
  public Page<Reply> page(int p, int size) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return replyRepository.findAll(pageable);
  }

  /**
   * 查询用户的回复列表
   *
   * @param p
   * @param size
   * @param user
   * @return
   */
  @Cacheable
  public Page<Reply> findByUser(int p, int size, User user) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return replyRepository.findByUser(user, pageable);
  }
}
