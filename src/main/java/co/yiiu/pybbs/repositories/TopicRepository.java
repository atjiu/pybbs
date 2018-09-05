package co.yiiu.pybbs.repositories;

import co.yiiu.pybbs.models.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tomoya at 2018/9/4
 */
public interface TopicRepository extends MongoRepository<Topic, String> {
}
