package co.yiiu.module.topic.repository;

import co.yiiu.module.topic.model.TopicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya at 2018/3/27
 */
@Repository
public interface TopicTagRepository extends JpaRepository<TopicTag, Long> {

  List<TopicTag> findByTopicId(Integer topicId);

  void deleteByTopicId(Integer topicId);
}
