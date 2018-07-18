package co.yiiu.module.topic.repository;

import co.yiiu.module.topic.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer>, JpaSpecificationExecutor<Topic> {

  @Query(value = "select t as topic, u as user from Topic t, User u where t.userId = u.id and t.userId = ?1",
      countQuery = "select count(1) from Topic t, User u where t.userId = u.id and t.userId = ?1")
  Page<Map> findByUserId(Integer userId, Pageable pageable);

  void deleteByUserId(Integer userId);

  @Query(value = "select t as topic, u as user from Topic t, User u where t.userId = u.id",
      countQuery = "select count(1) from Topic t, User u where t.userId = u.id")
  Page<Map> findTopics(Pageable pageable);

  @Query(value = "select t as topic, u as user from Topic t, User u where t.userId = u.id and t.good = ?1",
      countQuery = "select count(1) from Topic t, User u where t.userId = u.id and t.good = ?1")
  Page<Map> findByGood(Boolean b, Pageable pageable);

  @Query(value = "select t as topic, u as user from Topic t, User u where t.userId = u.id and t.commentCount = ?1",
      countQuery = "select count(1) from Topic t, User u where t.userId = u.id and t.commentCount = ?1")
  Page<Map> findByCommentCount(Integer commentCount, Pageable pageable);

  Topic findByTitle(String title);

  void delete(Topic topic);

  @Query(value = "select t as topic, u as user from Topic t, User u where t.userId = u.id",
      countQuery = "select count(1) from Topic t, User u where t.userId = u.id")
  Page<Map> findAllForAdmin(Pageable pageable);

  @Query(value = "select t as topic, u as user from Topic t, User u, TopicTag tt where t.userId = u.id and t.id = tt.topicId and tt.tagId = ?1",
      countQuery = "select count(1) from Topic t, User u, TopicTag tt where t.userId = u.id and t.id = tt.topicId and tt.tagId = ?1")
  Page<Map> findTopicsByTagId(Integer tagId, Pageable pageable);

}
