package co.yiiu.module.user.mapper;

import co.yiiu.module.user.pojo.RememberMeToken;

public interface RememberMeTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RememberMeToken record);

    int insertSelective(RememberMeToken record);

    RememberMeToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RememberMeToken record);

    int updateByPrimaryKey(RememberMeToken record);
}