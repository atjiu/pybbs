package co.yiiu.module.user.service;

import co.yiiu.module.user.mapper.OAuth2UserMapper;
import co.yiiu.module.user.pojo.OAuth2User;
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
  private OAuth2UserMapper oAuth2UserMapper;

  public void save(OAuth2User oAuth2User) {
    oAuth2UserMapper.insertSelective(oAuth2User);
  }

  public void createOAuth2User(String nickName, String avatar, Integer userId, String oauthUserId,
                                     String accessToken, String type) {
    OAuth2User oAuth2User = new OAuth2User();
    oAuth2User.setNickName(nickName);
    oAuth2User.setUserId(userId);
    oAuth2User.setType(type);
    oAuth2User.setInTime(new Date());
    oAuth2User.setOauthUserId(oauthUserId);
    oAuth2User.setAccessToken(accessToken);
    oAuth2User.setAvatar(avatar);
    this.save(oAuth2User);
  }

  public OAuth2User findByOauthUserIdAndType(String oauthUserId, String type) {
    return oAuth2UserMapper.findByOauthUserIdAndType(oauthUserId, type);
  }
}
