package co.yiiu.module.log.mapper;

import co.yiiu.module.log.pojo.Log;
import co.yiiu.module.log.pojo.LogWithBLOBs;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogWithBLOBs record);

    int insertSelective(LogWithBLOBs record);

    LogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LogWithBLOBs record);

    int updateByPrimaryKey(Log record);
}