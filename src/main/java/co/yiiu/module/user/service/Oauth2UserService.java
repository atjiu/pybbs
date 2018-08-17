package co.yiiu.module.user.service;

import co.yiiu.module.user.mapper.Oauth2UserMapper;
import co.yiiu.module.user.pojo.Oauth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tomoya at 2018/3/19
 */
@Service
@Transactional
public class Oauth2UserService {

  @Autowired
  private Oauth2UserMapper oauth2UserMapper;

  public void save(Oauth2User oauth2User) {
    oauth2UserMapper.insertSelective(oauth2User);
  }

  public void update(Oauth2User oauth2User) {
    oauth2UserMapper.updateByPrimaryKeySelective(oauth2User);
  }

  public void createOAuth2User(String nickName, String avatar, Integer userId, String oauthUserId,
                               String accessToken, String type) {
    Oauth2User oauth2User = new Oauth2User();
    oauth2User.setNickName(nickName);
    oauth2User.setUserId(userId);
    oauth2User.setType(type);
    oauth2User.setInTime(new Date());
    oauth2User.setOauthUserId(oauthUserId);
    oauth2User.setAccessToken(accessToken);
    oauth2User.setAvatar(avatar);
    this.save(oauth2User);
  }

  public Oauth2User findByOauthUserIdAndType(String oauthUserId, String type) {
    return oauth2UserMapper.findByOauthUserIdAndType(oauthUserId, type);
  }
}
