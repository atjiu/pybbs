package co.yiiu.module.tag.repository;

import co.yiiu.module.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya at 2018/3/27
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

  Tag findByName(String name);

  @Query(value = "select t from Tag t, TopicTag tt where t.id = tt.tagId and tt.topicId = ?1")
  List<Tag> findByTopicId(Integer topicId);

}
