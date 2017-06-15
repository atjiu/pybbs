package cn.tomoya.module.attendance.service;

import cn.tomoya.module.attendance.dao.AttendanceDao;
import cn.tomoya.module.attendance.entity.Attendance;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tomoya on 17-6-15.
 */
@Service
@Transactional
public class AttendanceService {

  @Autowired
  private AttendanceDao attendanceDao;

  /**
   * search between date1 and date2 record
   * @param user
   * @param date1
   * @param date2
   * @return
   */
  public Attendance findByUserAndInTime(User user, Date date1, Date date2) {
    return attendanceDao.findByUserAndInTimeBetween(user, date1, date2);
  }

  /**
   * save attendance
   * @param attendance
   */
  public void save(Attendance attendance) {
    attendanceDao.save(attendance);
  }
}
