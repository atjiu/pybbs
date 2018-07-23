package co.yiiu.module.code.mapper;

import co.yiiu.module.code.pojo.Code;

public interface CodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Code record);

    int insertSelective(Code record);

    Code selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);
}