package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.CommentMapper;
import co.yiiu.pybbs.model.Comment;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

  // 根据话题id查询评论
  public List<Map<String, Object>> selectByTopicId(Integer topicId) {
    return commentMapper.selectByTopicId(topicId);
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

    // 通知 TODO

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

  // ---------------------------- admin ----------------------------

  public IPage<Map<String, Object>> selectAllForAdmin(Integer pageNo, String startDate, String endDate, String username) {
    IPage<Map<String, Object>> iPage = new Page<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("pageSize")));
    return commentMapper.selectAllForAdmin(iPage, startDate, endDate, username);
  }
}
