package cn.tomoya.module.attendance.dao;

import cn.tomoya.module.attendance.entity.Attendance;
import cn.tomoya.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by tomoya on 17-6-15.
 */
public interface AttendanceDao extends JpaRepository<Attendance, Integer> {

  Attendance findByUserAndInTimeBetween(User user, Date date1, Date date2);

}
