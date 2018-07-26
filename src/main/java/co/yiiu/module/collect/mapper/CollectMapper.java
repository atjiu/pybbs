package co.yiiu.module.collect.mapper;

import co.yiiu.module.collect.pojo.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CollectMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(Collect record);

  int insertSelective(Collect record);

  Collect selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Collect record);

  int updateByPrimaryKey(Collect record);

  //自定义方法
  List<Map> findByUserId(
      @Param("userId") Integer userId,
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int countByUserId(Integer userId);

  int countByTopicId(Integer topicId);

  Collect findByUserIdAndTopicId(@Param("userId") Integer userId, @Param("topicId") Integer topicId);

  void deleteByUserId(Integer userId);

  void deleteByTopicId(Integer topicId);
}