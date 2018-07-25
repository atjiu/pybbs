package co.yiiu.module.code.mapper;

import co.yiiu.module.code.pojo.Code;

import java.util.List;

public interface CodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Code record);

    int insertSelective(Code record);

    Code selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);

    // 自定义方法
    List<Code> findByEmailAndCodeAndType(String email, String code, String type);
}