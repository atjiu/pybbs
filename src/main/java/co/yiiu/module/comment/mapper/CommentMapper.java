package co.yiiu.module.comment.mapper;

import co.yiiu.module.comment.pojo.Comment;
import co.yiiu.module.comment.pojo.CommentWithBLOBs;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentWithBLOBs record);

    int insertSelective(CommentWithBLOBs record);

    CommentWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CommentWithBLOBs record);

    int updateByPrimaryKey(Comment record);
}