package co.yiiu.config;

/**
 * Created by tomoya on 17-6-15.
 */
public class CookieConfig {

  private String domain;
  private int attendanceMaxAge;
  // attendance name
  private String attendanceName;
  private int rememberMeExpireDay;

  public int getRememberMeExpireDay() {
    return rememberMeExpireDay;
  }

  public void setRememberMeExpireDay(int rememberMeExpireDay) {
    this.rememberMeExpireDay = rememberMeExpireDay;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public int getAttendanceMaxAge() {
    return attendanceMaxAge;
  }

  public void setAttendanceMaxAge(int attendanceMaxAge) {
    this.attendanceMaxAge = attendanceMaxAge;
  }

  public String getAttendanceName() {
    return attendanceName;
  }

  public void setAttendanceName(String attendanceName) {
    this.attendanceName = attendanceName;
  }
}
