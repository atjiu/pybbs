package co.yiiu.pybbs.repositories;

import co.yiiu.pybbs.models.Collect;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectRepository extends MongoRepository<Collect, String> {

  void deleteByTopicId(String topicId);

  void deleteByUserId(String userId);
}
