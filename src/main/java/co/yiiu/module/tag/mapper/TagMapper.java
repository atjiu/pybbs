package co.yiiu.module.tag.mapper;

import co.yiiu.module.tag.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(Tag record);

  int insertSelective(Tag record);

  Tag selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Tag record);

  int updateByPrimaryKey(Tag record);

  //自定义方法
  List<Tag> findAll(
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int count();

  Tag findByName(String name);

  List<Tag> findByTopicId(Integer topicId);

  List<Tag> findByNameLike(
      @Param("name") String name,
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize
  );
}