package co.yiiu.module.topic.mapper;

import co.yiiu.module.topic.pojo.Topic;
import co.yiiu.module.topic.pojo.TopicWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TopicMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(TopicWithBLOBs record);

  int insertSelective(TopicWithBLOBs record);

  TopicWithBLOBs selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(TopicWithBLOBs record);

  int updateByPrimaryKeyWithBLOBs(TopicWithBLOBs record);

  int updateByPrimaryKey(Topic record);

  //自定义方法
  List<Map> findTopic(
      @Param("userId") Integer userId,
      @Param("good") Boolean good,
      @Param("top") Boolean top,
      @Param("commentCount") Integer commentCount,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime,
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int countTopic(
      @Param("userId") Integer userId,
      @Param("good") Boolean good,
      @Param("top") Boolean top,
      @Param("commentCount") Integer commentCount,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime
  );

  void deleteByUserId(Integer userId);

  Topic findByTitle(String title);

  List<Map> findTopicsByTagId(
      @Param("tagId") Integer tagId,
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int countTopicsByTagId(Integer tagId);

  List<TopicWithBLOBs> findAll();
}