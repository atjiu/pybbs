package cn.jfinalbbs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by liuyang on 15/4/2.
 */
public class DateUtil {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static String formatDateTime(Date date) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    /**
     * 字符串转时间
     * @param dateString
     * @param style
     * @return
     */
    public static Date string2Date(String dateString, String style) {
        if (StrUtil.isBlank(dateString)) return null;
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
     * @param date
     * @return
     */
    public static boolean isExpire(Date date) {
        if(date.before(new Date())) return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isExpire(DateUtil.string2Date("2015-04-09 20:57:15", FORMAT_DATETIME)));
    }

    public static Date getHourAfter(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(11, hour);
        return calendar.getTime();
    }

    public static Date getHourBefore(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(11, -hour);
        return calendar.getTime();
    }

    public static Date getDateBefore(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(5, -day);
        return calendar.getTime();
    }

    public static Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) + day);
        return now.getTime();
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
