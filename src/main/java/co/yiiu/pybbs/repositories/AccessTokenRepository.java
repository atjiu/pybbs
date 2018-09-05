package co.yiiu.pybbs.repositories;

import co.yiiu.pybbs.models.AccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccessTokenRepository extends MongoRepository<AccessToken, String> {

  void deleteByUserId(String userId);
}
