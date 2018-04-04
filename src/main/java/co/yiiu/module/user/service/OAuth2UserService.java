package co.yiiu.module.user.service;

import co.yiiu.module.user.model.OAuth2User;
import co.yiiu.module.user.repository.OAuth2UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class OAuth2UserService {

  @Autowired
  private OAuth2UserRepository oAuth2UserRepository;

  public OAuth2User save(OAuth2User oAuth2User) {
    return oAuth2UserRepository.save(oAuth2User);
  }

  public OAuth2User createOAuth2User(String nickName, String avatar, Integer userId, String oauthUserId,
                                     String accessToken, String type) {
    OAuth2User oAuth2User = new OAuth2User();
    oAuth2User.setNickName(nickName);
    oAuth2User.setUserId(userId);
    oAuth2User.setType(type);
    oAuth2User.setInTime(new Date());
    oAuth2User.setOauthUserId(oauthUserId);
    oAuth2User.setAccessToken(accessToken);
    oAuth2User.setAvatar(avatar);
    return this.save(oAuth2User);
  }

  public OAuth2User findByOauthUserIdAndType(String oauthUserId, String type) {
    return oAuth2UserRepository.findByOauthUserIdAndType(oauthUserId, type);
  }
}
