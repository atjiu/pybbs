package co.yiiu.module.log.mapper;

import co.yiiu.module.log.pojo.Log;
import co.yiiu.module.log.pojo.LogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogWithBLOBs record);

    int insertSelective(LogWithBLOBs record);

    LogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LogWithBLOBs record);

    int updateByPrimaryKey(Log record);

    // 自定义方法
    List<LogWithBLOBs> findByUserId(
        @Param("userId") Integer userId,
        @Param("pageNo") Integer pageNo,
        @Param("pageSize") Integer pageSize,
        @Param("orderBy") String orderBy
    );

    int countByUserId(Integer userId);

    void deleteByUserId(Integer userId);

    List<Map> findAllForAdmin(
        @Param("pageNo") Integer pageNo,
        @Param("pageSize") Integer pageSize,
        @Param("orderBy") String orderBy
    );

    int countAllForAdmin();
}