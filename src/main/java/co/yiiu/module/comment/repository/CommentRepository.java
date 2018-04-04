package co.yiiu.module.comment.repository;

import co.yiiu.module.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  @Query(value = "select c as comment, u as user from Comment c, User u where c.userId = u.id and c.topicId = ?1",
    countQuery = "select count(1) from Comment c, User u where c.userId = u.id and c.topicId = ?1")
  List<Map> findCommentWithTopic(Integer topicId);

  void deleteByTopicId(Integer topicId);

  void deleteByUserId(Integer userId);

  @Query(value = "select c as comment, u as user, t as topic from Comment c, User u, Topic t where c.userId = u.id and c.topicId = t.id and c.userId = ?1",
      countQuery = "select count(1) from Comment c, User u, Topic t where c.userId = u.id and c.topicId = t.id and c.userId = ?1")
  Page<Map> findByUserId(Integer userId, Pageable pageable);

  @Query(value = "select c as comment, u as user, t as topic from Comment c, User u, Topic t where c.userId = u.id and t.id = c.topicId",
      countQuery = "select count(1) from Comment c, User u, Topic t where c.userId = u.id and t.id = c.topicId")
  Page<Map> findAllForAdmin(Pageable pageable);

  List<Comment> findByTopicId(Integer topicId);
}
