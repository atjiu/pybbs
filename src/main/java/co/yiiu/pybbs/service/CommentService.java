package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.CommentMapper;
import co.yiiu.pybbs.model.Comment;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
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

  // 根据话题id查询评论
  public List<Map<String, Object>> selectByTopicId(Integer topicId) {
    List<Map<String, Object>> maps = commentMapper.selectByTopicId(topicId);
    if (Integer.parseInt(systemConfigService.selectAllConfig().get("commentLayer").toString()) == 1) {
      maps = this.sortByLayer(maps);
    }
    return maps;
  }

  // 删除话题时删除相关的评论
  public void deleteByTopicId(Integer topicId) {
    QueryWrapper<Comment> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Comment::getTopicId, topicId);
    commentMapper.delete(wrapper);
  }

  // 根据用户id删除评论记录
  public void deleteByUserId(Integer userId) {
    QueryWrapper<Comment> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(Comment::getUserId, userId);
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
    user.setScore(user.getScore() + Integer.parseInt(systemConfigService.selectAllConfig().get("createCommentScore").toString()));
    userService.update(user);
    if (session != null) session.setAttribute("_user", user);

    // 通知
    // 给评论的作者发通知
    if (commentId != null) {
      Comment targetComment = this.selectById(commentId);
      if (!user.getId().equals(targetComment.getUserId())) {
        notificationService.insert(user.getId(), targetComment.getUserId(), topic.getId(), "REPLY", content);
      }
    }
    // 给话题作者发通知
    if (!user.getId().equals(topic.getUserId())) {
      notificationService.insert(user.getId(), topic.getUserId(), topic.getId(), "COMMENT", content);
    }

    // 日志 TODO

    return comment;
  }

  public Comment selectById(Integer id) {
    return commentMapper.selectById(id);
  }

  // 更新评论
  public void update(Comment comment) {
    commentMapper.updateById(comment);
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
      userScore -= Integer.parseInt(systemConfigService.selectAllConfig().get("upCommentScore").toString());
    } else { // 点赞行为
      strings.add(String.valueOf(user.getId()));
      userScore += Integer.parseInt(systemConfigService.selectAllConfig().get("upCommentScore").toString());
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
      user.setScore(user.getScore() - Integer.parseInt(systemConfigService.selectAllConfig().get("deleteCommentScore").toString()));
      userService.update(user);
      if (session != null) session.setAttribute("_user", user);
      // 删除评论
      commentMapper.deleteById(id);
    }
  }

  // 查询用户的评论
  public IPage<Map<String, Object>> selectByUserId(Integer userId, Integer pageNo, Integer pageSize) {
    IPage<Map<String, Object>> iPage = new Page<>(pageNo,
        pageSize == null ?
        Integer.parseInt(systemConfigService.selectAllConfig().get("pageSize").toString()) : pageSize
    );
    return commentMapper.selectByUserId(iPage, userId);
  }

  // 盖楼排序
  public List<Map<String, Object>> sortByLayer(List<Map<String, Object>> comments) {
    List<Map<String, Object>> newComments = new ArrayList<>();
    comments.forEach(comment -> {
      if (comment.get("commentId") == null) {
        newComments.add(comment);
      } else {
        int index = this.findLastIndex(newComments, "commentId", (Integer) comment.get("commentId"));
        if (index == -1) {
          int upIndex = this.findLastIndex(newComments, "id", (Integer) comment.get("commentId"));
          if (upIndex == -1) {
            newComments.add(comment);
          } else {
            Long layer = (Long) newComments.get(upIndex).get("layer") + 1;
            comment.put("layer", layer);
            newComments.add(upIndex + 1, comment);
          }
        } else {
          Long layer = (Long) newComments.get(index).get("layer");
          comment.put("layer", layer);
          newComments.add(index + 1, comment);
        }
      }
    });
    return newComments;
  }

  // 从列表里查找指定值的下标
  private int findLastIndex(List<Map<String, Object>> newComments, String key, Integer value) {
    int index = -1;
    for (int i = 0; i < newComments.size(); i++) {
      if (newComments.get(i).get(key) == value) {
        index = i;
      }
    }
    return index;
  }

  // ---------------------------- admin ----------------------------

  public IPage<Map<String, Object>> selectAllForAdmin(Integer pageNo, String startDate, String endDate, String username) {
    IPage<Map<String, Object>> iPage = new Page<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("pageSize")));
    return commentMapper.selectAllForAdmin(iPage, startDate, endDate, username);
  }
}
