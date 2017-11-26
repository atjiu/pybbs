package co.yiiu.module.topic.repository;

import co.yiiu.module.node.model.Node;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

  Topic findById(int id);

  Page<Topic> findByUser(User user, Pageable pageable);

  void deleteByUser(User user);

  Page<Topic> findByGood(boolean b, Pageable pageable);

  Page<Topic> findByReplyCount(int i, Pageable pageable);

  Page<Topic> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

  int countByInTimeBetween(Date date1, Date date2);

  Topic findByTitle(String title);

  void delete(Topic topic);

  Page<Topic> findByNode(Node node, Pageable pageable);

  long countByNode(Node node);
}
