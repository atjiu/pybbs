package co.yiiu.module.comment.service;

import co.yiiu.config.MailTemplateConfig;
import co.yiiu.config.SiteConfig;
import co.yiiu.core.util.EmailUtil;
import co.yiiu.core.util.FreemarkerUtil;
import co.yiiu.core.util.JsonUtil;
import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.comment.repository.CommentRepository;
import co.yiiu.module.log.model.LogEventEnum;
import co.yiiu.module.log.model.LogTargetEnum;
import co.yiiu.module.log.service.LogService;
import co.yiiu.module.notification.model.NotificationEnum;
import co.yiiu.module.notification.service.NotificationService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.model.VoteAction;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.model.UserReputation;
import co.yiiu.module.user.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private TopicService topicService;
  @Autowired
  private LogService logService;
  @Autowired
  private UserService userService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private EmailUtil emailUtil;
  @Autowired
  private FreemarkerUtil freemarkerUtil;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private MailTemplateConfig mailTemplateConfig;

  public Comment findById(Integer id) {
    return commentRepository.findById(id).get();
  }

  public void save(Comment comment) {
    commentRepository.save(comment);
  }

  public Comment update(Topic topic, Comment oldComment, Comment comment, Integer userId) {
    this.save(comment);
    // 日志
    logService.save(LogEventEnum.EDIT_COMMENT, userId, LogTargetEnum.COMMENT.name(), comment.getId(),
        JsonUtil.objectToJson(oldComment), JsonUtil.objectToJson(comment), topic);
    return comment;
  }

  public void delete(Integer id, Integer userId) {
    Comment comment = findById(id);
    if (comment != null) {
      Topic topic = topicService.findById(comment.getTopicId());
      topic.setCommentCount(topic.getCommentCount() - 1);
      topicService.save(topic);
      // 日志
      logService.save(LogEventEnum.DELETE_COMMENT, userId, LogTargetEnum.COMMENT.name(), comment.getId(), JsonUtil.objectToJson(comment), null, topic);
      commentRepository.deleteById(id);
      // 计算weight
      topicService.weight(topic, null);
    }
  }

  /**
   * 删除用户发布的所有评论
   * 不做日志记录 原因 {@link TopicService#deleteByUserId(Integer)}
   *
   * @param userId
   */
  public void deleteByUserId(Integer userId) {
    commentRepository.deleteByUserId(userId);
  }

  /**
   * 根据话题删除评论
   *
   * @param topic
   */
  public void deleteByTopic(Topic topic) {
    commentRepository.deleteByTopicId(topic.getId());
  }

  public Comment createComment(Integer userId, Topic topic, Integer commentId, String content) {
    Comment comment = new Comment();
    comment.setCommentId(commentId);
    comment.setUserId(userId);
    comment.setTopicId(topic.getId());
    comment.setInTime(new Date());
    comment.setUp(0);
    comment.setDown(0);
    comment.setUpIds("");
    comment.setDownIds("");
    comment.setContent(Jsoup.clean(content, Whitelist.relaxed()));
    this.save(comment);

    //评论+1
    topic.setCommentCount(topic.getCommentCount() + 1);
    topic.setLastCommentTime(new Date());
    topicService.save(topic);

    // 通知
    User commentUser = userService.findById(comment.getUserId());
    if (commentId != null) {
      Comment replyComment = this.findById(commentId);
      if(!userId.equals(replyComment.getUserId())) {
        notificationService.sendNotification(userId, replyComment.getUserId(), NotificationEnum.REPLY, topic.getId(), content);
        // 邮件
        // 被回复的用户
        User _commentUser = userService.findById(replyComment.getUserId());
        if(_commentUser.getReplyEmail() && !StringUtils.isEmpty(_commentUser.getEmail())) {
          Map params = Maps.newHashMap();
          params.put("username", commentUser.getUsername());
          params.put("topic", topic);
          params.put("domain", siteConfig.getBaseUrl());
          params.put("content", content);
          String subject = freemarkerUtil.format((String) mailTemplateConfig.getReplyComment().get("subject"), params);
          String emailContent = freemarkerUtil.format((String) mailTemplateConfig.getReplyComment().get("content"), params);
          emailUtil.sendEmail(_commentUser.getEmail(), subject, emailContent);
        }
      }
    }
    if (!topic.getUserId().equals(userId)) {
      notificationService.sendNotification(userId, topic.getUserId(), NotificationEnum.COMMENT, topic.getId(), content);
      // 邮件
      User topicUser = userService.findById(topic.getUserId());
      if(topicUser.getCommentEmail() && !StringUtils.isEmpty(topicUser.getEmail())) {
        Map params = Maps.newHashMap();
        params.put("username", commentUser.getUsername());
        params.put("topic", topic);
        params.put("domain", siteConfig.getBaseUrl());
        params.put("content", content);
        String subject = freemarkerUtil.format((String) mailTemplateConfig.getCommentTopic().get("subject"), params);
        String emailContent = freemarkerUtil.format((String) mailTemplateConfig.getCommentTopic().get("content"), params);
        emailUtil.sendEmail(topicUser.getEmail(), subject, emailContent);
      }
    }

    return comment;
  }

  /**
   * 对评论投票
   *
   * @param userId
   * @param comment
   */
  public Map<String, Object> vote(Integer userId, Comment comment, String action) {
    Map<String, Object> map = new HashMap<>();
    List<String> upIds = new ArrayList<>();
    List<String> downIds = new ArrayList<>();
    LogEventEnum logEventEnum = null;
    NotificationEnum notificationEnum = null;
    User commentUser = userService.findById(comment.getUserId());
    if (!StringUtils.isEmpty(comment.getUpIds())) {
      upIds = Lists.newArrayList(comment.getUpIds().split(","));
    }
    if (!StringUtils.isEmpty(comment.getDownIds())) {
      downIds = Lists.newArrayList(comment.getDownIds().split(","));
    }
    if (action.equals(VoteAction.UP.name())) {
      logEventEnum = LogEventEnum.UP_COMMENT;
      notificationEnum = NotificationEnum.UP_COMMENT;
      // 如果点踩ID里有，就删除，并将down - 1
      if (downIds.contains(String.valueOf(userId))) {
        commentUser.setReputation(commentUser.getReputation() + UserReputation.DOWN_COMMENT.getReputation());
        comment.setDown(comment.getDown() - 1);
        downIds.remove(String.valueOf(userId));
      }
      // 如果点赞ID里没有，就添加上，并将up + 1
      if (!upIds.contains(String.valueOf(userId))) {
        commentUser.setReputation(commentUser.getReputation() + UserReputation.UP_COMMENT.getReputation());
        upIds.add(String.valueOf(userId));
        comment.setUp(comment.getUp() + 1);
        map.put("isUp", true);
        map.put("isDown", false);
      } else {
        commentUser.setReputation(commentUser.getReputation() - UserReputation.UP_COMMENT.getReputation());
        upIds.remove(String.valueOf(userId));
        comment.setUp(comment.getUp() - 1);
        map.put("isUp", false);
        map.put("isDown", false);
      }
    } else if (action.equals(VoteAction.DOWN.name())) {
      logEventEnum = LogEventEnum.DOWN_COMMENT;
      notificationEnum = NotificationEnum.DOWN_COMMENT;
      // 如果点赞ID里有，就删除，并将up - 1
      if (upIds.contains(String.valueOf(userId))) {
        commentUser.setReputation(commentUser.getReputation() - UserReputation.UP_COMMENT.getReputation());
        comment.setUp(comment.getUp() - 1);
        upIds.remove(String.valueOf(userId));
      }
      // 如果点踩ID里没有，就添加上，并将down + 1
      if (!downIds.contains(String.valueOf(userId))) {
        commentUser.setReputation(commentUser.getReputation() + UserReputation.DOWN_COMMENT.getReputation());
        downIds.add(String.valueOf(userId));
        comment.setDown(comment.getDown() + 1);
        map.put("isUp", false);
        map.put("isDown", true);
      } else {
        commentUser.setReputation(commentUser.getReputation() - UserReputation.DOWN_COMMENT.getReputation());
        downIds.remove(String.valueOf(userId));
        comment.setDown(comment.getDown() - 1);
        map.put("isUp", false);
        map.put("isDown", false);
      }
    }
    map.put("commentId", comment.getId());
    map.put("up", comment.getUp());
    map.put("down", comment.getDown());
    map.put("vote", comment.getUp() - comment.getDown());
    comment.setUpIds(StringUtils.collectionToCommaDelimitedString(upIds));
    comment.setDownIds(StringUtils.collectionToCommaDelimitedString(downIds));
    save(comment);
    // 通知
    notificationService.sendNotification(userId, commentUser.getId(), notificationEnum, comment.getTopicId(), null);
    // 记录日志
    Topic topic = topicService.findById(comment.getTopicId());
    logService.save(logEventEnum, userId, LogTargetEnum.COMMENT.name(), comment.getId(), null, null, topic);
    // 计算weight
    topicService.weight(topic, null);
    return map;
  }

  /**
   * 根据话题查询评论列表
   *
   * @param topicId
   * @return
   */
  public List<Map> findCommentWithTopic(Integer topicId) {
    List<Map> comments = commentRepository.findCommentWithTopic(topicId);
    return sortLayer(comments, new ArrayList<>(), 1); // 初始深度为1
  }

  public List<Comment> findByTopicId(Integer topicId) {
    return commentRepository.findByTopicId(topicId);
  }

  private List<Map> sortLayer(List<Map> comments, List<Map> newComments, Integer layer) {
    if (comments == null || comments.size() == 0) {
      return newComments;
    }
    if (newComments.size() == 0) {
      comments.forEach(map -> {
        if (((Comment) map.get("comment")).getCommentId() == null) {
          ((Comment) map.get("comment")).setLayer(layer);
          newComments.add(map);
        }
      });
      comments.removeAll(newComments);
      return sortLayer(comments, newComments, layer + 1);
    } else {
      for (int index = 0; index < newComments.size(); index++) {
        Map newComment = newComments.get(index);

        List<Map> findComments = new ArrayList<>();
        comments.forEach(map -> {
          if (Objects.equals(((Comment) map.get("comment")).getCommentId(), ((Comment) newComment.get("comment")).getId())) {
            ((Comment) map.get("comment")).setLayer(layer);
            findComments.add(map);
          }
        });
        comments.removeAll(findComments);

        newComments.addAll(newComments.indexOf(newComment) + 1, findComments);
        index = newComments.indexOf(newComment) + findComments.size();
      }
      return sortLayer(comments, newComments, layer + 1);
    }
  }

  /**
   * 分页查询评论列表
   *
   * @param p
   * @param size
   * @return
   */
  public Page<Comment> page(int p, int size) {
    Sort sort = new Sort(Sort.Direction.DESC, "inTime");
    Pageable pageable = PageRequest.of(p - 1, size, sort);
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
  public Page<Map> findByUser(int p, int size, User user) {
    Sort sort = new Sort(Sort.Direction.DESC, "inTime");
    Pageable pageable = PageRequest.of(p - 1, size, sort);
    return commentRepository.findByUserId(user.getId(), pageable);
  }

  // 后台评论列表
  public Page<Map> findAllForAdmin(Integer pageNo, Integer pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, new Sort(Sort.Direction.DESC, "inTime"));
    return commentRepository.findAllForAdmin(pageable);
  }
}
