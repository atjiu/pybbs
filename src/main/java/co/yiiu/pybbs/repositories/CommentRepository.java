package co.yiiu.pybbs.repositories;

import co.yiiu.pybbs.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tomoya at 2018/9/4
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

  void deleteByTopicId(String topicId);
}
