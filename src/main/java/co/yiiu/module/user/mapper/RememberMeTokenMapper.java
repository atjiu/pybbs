package co.yiiu.module.user.mapper;

import co.yiiu.module.user.pojo.RememberMeToken;

import java.util.List;

public interface RememberMeTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RememberMeToken record);

    int insertSelective(RememberMeToken record);

    RememberMeToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RememberMeToken record);

    int updateByPrimaryKey(RememberMeToken record);

    //自定义方法
    RememberMeToken findBySeries(String series);

    void deleteByUsername(String username);

    List<RememberMeToken> findAllByUsername(String username);
}