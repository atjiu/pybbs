package co.yiiu.pybbs.service;

import co.yiiu.pybbs.config.service.EmailService;
import co.yiiu.pybbs.config.service.RedisService;
import co.yiiu.pybbs.mapper.CommentMapper;
import co.yiiu.pybbs.model.Comment;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.model.vo.CommentsByTopic;
import co.yiiu.pybbs.util.Constants;
import co.yiiu.pybbs.util.JsonUtil;
import co.yiiu.pybbs.util.MyPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;
  @Autowired
  private TopicService topicService;
  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private UserService userService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private EmailService emailService;
  @Autowired
  private RedisService redisService;

  // 根据话题id查询评论
  public List<CommentsByTopic> selectByTopicId(Integer topicId) {
    String commentsJson = redisService.getString(Constants.REDIS_COMMENTS_KEY + topicId);
    List<CommentsByTopic> commentsByTopics;
    if (commentsJson == null) {
      commentsByTopics = commentMapper.selectByTopicId(topicId);
//      BeanUtils.copyProperties(commentMapper.selectByTopicId(topicId), commentsByTopics);
      redisService.setString(Constants.REDIS_COMMENTS_KEY + topicId, JsonUtil.objectToJson(commentsByTopics));
    } else {
      // 带泛型转换, 这里如果不带泛型转换，会报错
      Type type = new TypeToken<List<CommentsByTopic>>(){}.getType();
      commentsByTopics = JsonUtil.jsonToObject(commentsJson, type);
    }

    if (Integer.parseInt(systemConfigService.selectAllConfig().get("comment_layer").toString()) == 1) {
      commentsByTopics = this.sortByLayer(commentsByTopics);
    }
    return commentsByTopics;
  }

  // 删除话题时删除相关的评论
  public void deleteByTopicId(Integer topicId) {
    QueryWrapper<Comment> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Comment::getTopicId, topicId);
    commentMapper.delete(wrapper);
    // 删除redis缓存
    redisService.delString(Constants.REDIS_COMMENTS_KEY + topicId);
  }

  // 根据用户id删除评论记录
  public void deleteByUserId(Integer userId) {
    QueryWrapper<Comment> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Comment::getUserId, userId);
    if (redisService.isRedisConfig()) { // 如果配置了redis，则清除redis里的缓存
      List<Comment> comments = commentMapper.selectList(wrapper);
      comments.forEach(comment ->
          // 删除redis缓存
          redisService.delString(Constants.REDIS_COMMENTS_KEY + comment.getTopicId()));
    }
    commentMapper.delete(wrapper);
  }

  // 保存评论
  public Comment insert(String content, Topic topic, User user, Integer commentId, HttpSession session) {
    Comment comment = new Comment();
    comment.setCommentId(commentId);
    comment.setContent(content);
    comment.setInTime(new Date());
    comment.setTopicId(topic.getId());
    comment.setUserId(user.getId());
    commentMapper.insert(comment);

    // 话题的评论数+1
    topic.setCommentCount(topic.getCommentCount() + 1);
    topicService.update(topic);

    // 增加用户积分
    user.setScore(user.getScore() + Integer.parseInt(systemConfigService.selectAllConfig().get("create_comment_score").toString()));
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);

    // 通知
    // 给评论的作者发通知
    if (commentId != null) {
      Comment targetComment = this.selectById(commentId);
      if (!user.getId().equals(targetComment.getUserId())) {
        notificationService.insert(user.getId(), targetComment.getUserId(), topic.getId(), "REPLY", content);

        String emailTitle = "你在话题 %s 下的评论被 %s 回复了，快去看看吧！";
        // 如果开启了websocket，就发网页通知
        if (systemConfigService.selectAllConfig().get("websocket").toString().equals("1")
            && Constants.usernameSocketIdMap.containsKey(targetComment.getUserId())) {
          Constants.websocketUserMap.get(Constants.usernameSocketIdMap.get(targetComment.getUserId())).getClient()
              .sendEvent("notifications", String.format(emailTitle, topic.getTitle(), user.getUsername()));
          Constants.websocketUserMap.get(Constants.usernameSocketIdMap.get(targetComment.getUserId())).getClient()
              .sendEvent("notification_notread", 1);
        }
        // 发送邮件通知
        User targetUser = userService.selectById(targetComment.getUserId());
        if (!StringUtils.isEmpty(targetUser.getEmail()) && targetUser.getEmailNotification()) {
          String emailContent = "回复内容: %s <br><a href='%s/topic/%s' target='_blank'>传送门</a>";
          new Thread(() -> emailService.sendEmail(
              targetUser.getEmail(),
              String.format(emailTitle, topic.getTitle(), user.getUsername()),
              String.format(emailContent, comment.getContent(), systemConfigService.selectAllConfig().get("base_url").toString(), topic.getId())
          )).start();
        }
      }
    }
    // 给话题作者发通知
    if (!user.getId().equals(topic.getUserId())) {
      notificationService.insert(user.getId(), topic.getUserId(), topic.getId(), "COMMENT", content);
      // 发送邮件通知
      String emailTitle = "%s 评论你的话题 %s 快去看看吧！";
      // 如果开启了websocket，就发网页通知
      if (systemConfigService.selectAllConfig().get("websocket").toString().equals("1")
          && Constants.usernameSocketIdMap.containsKey(topic.getUserId())) {
        Constants.websocketUserMap.get(Constants.usernameSocketIdMap.get(topic.getUserId())).getClient()
            .sendEvent("notifications", String.format(emailTitle, user.getUsername(), topic.getTitle()));
        Constants.websocketUserMap.get(Constants.usernameSocketIdMap.get(topic.getUserId())).getClient()
            .sendEvent("notification_notread", 1);
      }
      User targetUser = userService.selectById(topic.getUserId());
      if (!StringUtils.isEmpty(targetUser.getEmail()) && targetUser.getEmailNotification()) {
        String emailContent = "评论内容: %s <br><a href='%s/topic/%s' target='_blank'>传送门</a>";
        new Thread(() -> emailService.sendEmail(
            targetUser.getEmail(),
            String.format(emailTitle, user.getUsername(), topic.getTitle()),
            String.format(emailContent, comment.getContent(), systemConfigService.selectAllConfig().get("base_url").toString(), topic.getId())
        )).start();
      }
    }

    // 删除redis里关于当前评论话题的评论列表缓存
    redisService.delString(Constants.REDIS_COMMENTS_KEY + topic.getId());

    // 日志 TODO

    return comment;
  }

  public Comment selectById(Integer id) {
    return commentMapper.selectById(id);
  }

  // 更新评论
  public void update(Comment comment) {
    commentMapper.updateById(comment);
    // 删除redis里的缓存
    redisService.delString(Constants.REDIS_COMMENTS_KEY + comment.getTopicId());
  }

  // 对评论点赞
  public int vote(Comment comment, User user, HttpSession session) {
    String upIds = comment.getUpIds();
    // 将点赞用户id的字符串转成集合
    Set<String> strings = StringUtils.commaDelimitedListToSet(upIds);
    // 把新的点赞用户id添加进集合，这里用set，正好可以去重，如果集合里已经有用户的id了，那么这次动作被视为取消点赞
    Integer userScore = user.getScore();
    if (strings.contains(String.valueOf(user.getId()))) { // 取消点赞行为
      strings.remove(String.valueOf(user.getId()));
      userScore -= Integer.parseInt(systemConfigService.selectAllConfig().get("up_comment_score").toString());
    } else { // 点赞行为
      strings.add(String.valueOf(user.getId()));
      userScore += Integer.parseInt(systemConfigService.selectAllConfig().get("up_comment_score").toString());
    }
    // 再把这些id按逗号隔开组成字符串
    comment.setUpIds(StringUtils.collectionToCommaDelimitedString(strings));
    // 更新评论
    this.update(comment);
    // 增加用户积分
    user.setScore(userScore);
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);
    return strings.size();
  }

  // 删除评论
  public void delete(Integer id, HttpSession session) {
    Comment comment = this.selectById(id);
    if (comment != null) {
      // 话题评论数-1
      Topic topic = topicService.selectById(comment.getTopicId());
      topic.setCommentCount(topic.getCommentCount() - 1);
      topicService.update(topic);
      // 减去用户积分
      User user = userService.selectById(comment.getUserId());
      user.setScore(user.getScore() - Integer.parseInt(systemConfigService.selectAllConfig().get("delete_comment_score").toString()));
      userService.update(user);
      if (session != null) session.setAttribute("_user", user);
      // 删除redis里的缓存
      redisService.delString(Constants.REDIS_COMMENTS_KEY + comment.getTopicId());
      // 删除评论
      commentMapper.deleteById(id);
    }
  }

  // 查询用户的评论
  public MyPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo,
        pageSize == null ?
        Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString()) : pageSize
    );
    return commentMapper.selectByUserId(iPage, userId);
  }

  // 盖楼排序
  public List<CommentsByTopic> sortByLayer(List<CommentsByTopic> comments) {
    List<CommentsByTopic> newComments = new ArrayList<>();
    comments.forEach(comment -> {
      if (comment.getCommentId() == null) {
        newComments.add(comment);
      } else {
        int index = this.findLastIndex(newComments, "commentId", comment.getCommentId());
        if (index == -1) {
          int upIndex = this.findLastIndex(newComments, "id", comment.getCommentId());
          if (upIndex == -1) {
            newComments.add(comment);
          } else {
            int layer = newComments.get(upIndex).getLayer() + 1;
            comment.setLayer(layer);
            newComments.add(upIndex + 1, comment);
          }
        } else {
          int layer = newComments.get(index).getLayer();
          comment.setLayer(layer);
          newComments.add(index + 1, comment);
        }
      }
    });
    return newComments;
  }

  // 从列表里查找指定值的下标
  private int findLastIndex(List<CommentsByTopic> newComments, String key, Integer value) {
    int index = -1;
    for (int i = 0; i < newComments.size(); i++) {
      if (key.equals("commentId")) {
        if (value.equals(newComments.get(i).getCommentId())) {
          index = i;
        }
      } else if (key.equals("id")) {
        if (value.equals(newComments.get(i).getId())) {
          index = i;
        }
      }
    }
    return index;
  }

  // ---------------------------- admin ----------------------------

  public MyPage<Map<String, Object>> selectAllForAdmin(Integer pageNo, String startDate, String endDate, String username) {
    MyPage<Map<String, Object>> iPage = new MyPage<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("page_size")));
    return commentMapper.selectAllForAdmin(iPage, startDate, endDate, username);
  }

  // 查询今天新增的话题数
  public int countToday() {
    return commentMapper.countToday();
  }
}
