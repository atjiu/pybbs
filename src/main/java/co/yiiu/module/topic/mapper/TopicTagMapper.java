package co.yiiu.module.topic.mapper;

import co.yiiu.module.topic.pojo.TopicTag;

public interface TopicTagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicTag record);

    int insertSelective(TopicTag record);

    TopicTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicTag record);

    int updateByPrimaryKey(TopicTag record);
}