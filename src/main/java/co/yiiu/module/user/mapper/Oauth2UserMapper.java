package co.yiiu.module.user.mapper;

import co.yiiu.module.user.pojo.Oauth2User;

public interface Oauth2UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Oauth2User record);

    int insertSelective(Oauth2User record);

    Oauth2User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Oauth2User record);

    int updateByPrimaryKey(Oauth2User record);

    //自定义方法
    Oauth2User findByOauthUserIdAndType(String oauthUserId, String type);
}