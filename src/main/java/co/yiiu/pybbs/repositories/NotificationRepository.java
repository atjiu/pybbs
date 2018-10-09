package co.yiiu.pybbs.repositories;

import co.yiiu.pybbs.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tomoya at 2018/9/14
 */
public interface NotificationRepository extends MongoRepository<Notification, String> {
}
