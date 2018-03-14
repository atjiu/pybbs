package co.yiiu.module.comment.repository;

import co.yiiu.module.comment.model.Comment;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  List<Comment> findByTopicOrderByUpDownDescDownAscInTimeAsc(Topic topic);

  void deleteByTopic(Topic topic);

  void deleteByUser(User user);

  Page<Comment> findByUser(User user, Pageable pageable);
}
