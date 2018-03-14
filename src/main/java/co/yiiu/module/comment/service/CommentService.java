package co.yiiu.module.comment.service;

import co.yiiu.core.util.Constants;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.repository.CommentRepository;
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
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private TopicService topicService;

  /**
   * 根据id查询评论
   * @param id
   * @return
   */
  @Cacheable
  public Comment findById(int id) {
    return commentRepository.findOne(id);
  }

  /**
   * 保存评论
   * @param comment
   */
  @CacheEvict(allEntries = true)
  public void save(Comment comment) {
    commentRepository.save(comment);
  }

  /**
   * 根据id删除评论
   * @param id
   * @return
   */
  @CacheEvict(allEntries = true)
  public Map delete(int id) {
    Map map = new HashMap();
    Comment comment = findById(id);
    if (comment != null) {
      map.put("topicId", comment.getTopic().getId());
      Topic topic = comment.getTopic();
      topic.setCommentCount(topic.getCommentCount() - 1);
      topicService.save(topic);
      commentRepository.delete(id);
    }
    return map;
  }

  /**
   * 删除用户发布的所有评论
   *
   * @param user
   */
  @CacheEvict(allEntries = true)
  public void deleteByUser(User user) {
    commentRepository.deleteByUser(user);
  }

  /**
   * 根据话题删除评论
   *
   * @param topic
   */
  @CacheEvict(allEntries = true)
  public void deleteByTopic(Topic topic) {
    commentRepository.deleteByTopic(topic);
  }

  /**
   * 赞
   *
   * @param userId
   * @param comment
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Comment up(int userId, Comment comment) {
    String upIds = comment.getUpIds();
    if (upIds == null) upIds = Constants.COMMA;
    if (!upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
      comment.setUp(comment.getUp() + 1);
      comment.setUpIds(upIds + userId + Constants.COMMA);

      String downIds = comment.getDownIds();
      if (downIds == null) downIds = Constants.COMMA;
      if (downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        comment.setDown(comment.getDown() - 1);
        downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        comment.setDownIds(downIds);
      }
      int count = comment.getUp() - comment.getDown();
      comment.setUpDown(count > 0 ? count : 0);
      save(comment);
    }
    return comment;
  }

  /**
   * 取消赞
   *
   * @param userId
   * @param commentId
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Comment cancelUp(int userId, int commentId) {
    Comment comment = findById(commentId);
    if (comment != null) {
      String upIds = comment.getUpIds();
      if (upIds == null) upIds = Constants.COMMA;
      if (upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        comment.setUp(comment.getUp() - 1);
        upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        comment.setUpIds(upIds);

        int count = comment.getUp() - comment.getDown();
        comment.setUpDown(count > 0 ? count : 0);
        save(comment);
      }
    }
    return comment;
  }

  /**
   * 踩
   *
   * @param userId
   * @param comment
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Comment down(int userId, Comment comment) {
    String downIds = comment.getDownIds();
    if (downIds == null) downIds = Constants.COMMA;
    if (!downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
      comment.setDown(comment.getDown() + 1);
      comment.setDownIds(downIds + userId + Constants.COMMA);

      String upIds = comment.getUpIds();
      if (upIds == null) upIds = Constants.COMMA;
      if (upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        comment.setUp(comment.getUp() - 1);
        upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        comment.setUpIds(upIds);
      }
      int count = comment.getUp() - comment.getDown();
      comment.setUpDown(count > 0 ? count : 0);
      save(comment);
    }
    return comment;
  }

  /**
   * 取消踩
   *
   * @param userId
   * @param commentId
   */
  @CacheEvict(allEntries = true) //有更新操作都要清一下缓存
  public Comment cancelDown(int userId, int commentId) {
    Comment comment = findById(commentId);
    if (comment != null) {
      String downIds = comment.getDownIds();
      if (downIds == null) downIds = Constants.COMMA;
      if (downIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
        comment.setDown(comment.getDown() - 1);
        downIds = downIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
        comment.setDownIds(downIds);

        int count = comment.getUp() - comment.getDown();
        comment.setUpDown(count > 0 ? count : 0);
        save(comment);
      }
    }
    return comment;
  }

  /**
   * 根据话题查询评论列表
   *
   * @param topic
   * @return
   */
  @Cacheable
  public List<Comment> findByTopic(Topic topic) {
    return commentRepository.findByTopicOrderByUpDownDescDownAscInTimeAsc(topic);
  }

  /**
   * 分页查询评论列表
   *
   * @param p
   * @param size
   * @return
   */
  @Cacheable
  public Page<Comment> page(int p, int size) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return commentRepository.findAll(pageable);
  }

  /**
   * 查询用户的评论列表
   *
   * @param p
   * @param size
   * @param user
   * @return
   */
  @Cacheable
  public Page<Comment> findByUser(int p, int size, User user) {
    Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "inTime"));
    Pageable pageable = new PageRequest(p - 1, size, sort);
    return commentRepository.findByUser(user, pageable);
  }
}
