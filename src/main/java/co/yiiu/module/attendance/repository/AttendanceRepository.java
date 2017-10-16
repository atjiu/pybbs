package co.yiiu.module.attendance.repository;

import co.yiiu.module.attendance.model.Attendance;
import co.yiiu.module.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by tomoya on 17-6-15.
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

  Attendance findByUserAndInTimeBetween(User user, Date date1, Date date2);

}
