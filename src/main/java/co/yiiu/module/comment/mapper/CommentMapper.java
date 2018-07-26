package co.yiiu.module.comment.mapper;

import co.yiiu.module.comment.pojo.Comment;
import co.yiiu.module.comment.pojo.CommentWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(CommentWithBLOBs record);

  int insertSelective(CommentWithBLOBs record);

  CommentWithBLOBs selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(CommentWithBLOBs record);

  int updateByPrimaryKeyWithBLOBs(CommentWithBLOBs record);

  int updateByPrimaryKey(Comment record);

  //自定义方法
  List<Map> findByTopicId(Integer topicId);

  int countByTopicId(Integer topicId);

  void deleteByTopicId(Integer topicId);

  void deleteByUserId(Integer userId);

  List<Map> findAllForAdmin(
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int countAllForAdmin();

  List<CommentWithBLOBs> findCommentByTopicId(Integer topicId);

  List<Map> findByUserId(
      @Param("userId") Integer userId,
      @Param("pageNo") Integer pageNo,
      @Param("pageSize") Integer pageSize,
      @Param("orderBy") String orderBy
  );

  int countByUserId(Integer userId);
}