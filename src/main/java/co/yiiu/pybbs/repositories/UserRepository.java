package co.yiiu.pybbs.repositories;

import co.yiiu.pybbs.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tomoya at 2018/9/3
 */
public interface UserRepository extends MongoRepository<User, String> {
}
