package co.yiiu.module.user.mapper;

import co.yiiu.module.user.pojo.OAuth2User;

public interface OAuth2UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OAuth2User record);

    int insertSelective(OAuth2User record);

    OAuth2User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OAuth2User record);

    int updateByPrimaryKey(OAuth2User record);

    //自定义方法
    OAuth2User findByOauthUserIdAndType(String oauthUserId, String type);
}