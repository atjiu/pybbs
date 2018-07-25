package co.yiiu.module.attachment.mapper;

import co.yiiu.module.attachment.pojo.Attachment;

public interface AttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);

    //自定义方法
    Attachment findByMd5(String md5);
}