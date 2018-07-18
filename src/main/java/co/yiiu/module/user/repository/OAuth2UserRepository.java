package co.yiiu.module.user.repository;

import co.yiiu.module.user.model.OAuth2User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya at 2018/3/19
 */
@Repository
public interface OAuth2UserRepository extends JpaRepository<OAuth2User, Integer> {

  OAuth2User findByOauthUserIdAndType(String oauthUserId, String type);
}
