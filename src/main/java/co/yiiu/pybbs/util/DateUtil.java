package co.yiiu.pybbs.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class DateUtil {

  private DateUtil() {}

  public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_DATE = "yyyy-MM-dd";
  public static final String TIME_ZONE = "GMT+8";

  public static String formatDateTime(Date date) {
    return DateUtil.formatDateTime(date, DateUtil.FORMAT_DATETIME);
  }

  public static String formatDate(Date date) {
    return DateUtil.formatDateTime(date, DateUtil.FORMAT_DATE);
  }

  public static String formatDateTime(Date date, String style) {
    if (date == null) return null;
    SimpleDateFormat sdf = new SimpleDateFormat(style);
    sdf.setTimeZone(TimeZone.getTimeZone(DateUtil.TIME_ZONE));
    return sdf.format(date);
  }

  /**
   * 字符串转时间
   *
   * @param dateString
   * @param style
   * @return
   */
  public static Date string2Date(String dateString, String style) {
    if (StringUtils.isEmpty(dateString)) return null;
    Date date = new Date();
    SimpleDateFormat strToDate = new SimpleDateFormat(style);
    try {
      date = strToDate.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 判断传入的时间是否在当前时间之后，返回boolean值
   * true: 过期
   * false: 还没过期
   *
   * @param date
   * @return
   */
  public static boolean isExpire(Date date) {
    if (date.before(new Date())) return true;
    return false;
  }

  // 获取 hour 后的时间
  public static Date getHourAfter(Date date, int hour) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
    return calendar.getTime();
  }

  // 获取 hour 前的时间
  public static Date getHourBefore(Date date, int hour) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
    return calendar.getTime();
  }

  public static Date getDateBefore(Date date, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -day);
    return calendar.getTime();
  }

  public static Date getDateAfter(Date date, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, day);
    return calendar.getTime();
  }

  public static Date getMinuteAfter(Date date, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, minute);
    return calendar.getTime();
  }

  public static Date getMinuteBefore(Date date, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, -minute);
    return calendar.getTime();
  }

}
