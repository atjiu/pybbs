package co.yiiu.module.notification.mapper;

import co.yiiu.module.notification.pojo.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NotificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKeyWithBLOBs(Notification record);

    int updateByPrimaryKey(Notification record);

    //自定义方法
    List<Map> findByTargetUserId(
        @Param("targetUserId") Integer targetUserId,
        @Param("isRead") Boolean isRead,
        @Param("pageNo") Integer pageNo,
        @Param("pageSize") Integer pageSize,
        @Param("orderBy") String orderBy
    );

    int countByTargetUserId(@Param("targetUserId") Integer targetUserId, @Param("isRead") Boolean isRead);

    void updateByIsRead(Integer targetUserId);

    void deleteNotification(
        @Param("targetUserId") Integer targetUserId,
        @Param("userId") Integer userId,
        @Param("topicId") Integer topicId);

}