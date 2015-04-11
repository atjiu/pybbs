package cn.jfinalbbs.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void testGetHourAfter() throws Exception {
        System.out.println(DateUtil.getDateAfter(new Date(), 1));
    }

    @Test
    public void testGetHourBefore() throws Exception {
        System.out.println(DateUtil.getDateBefore(new Date(), 1));
    }

    @Test
    public void testGetDayBefore() throws Exception {
        System.out.println(DateUtil.getHourAfter(new Date(), 1));
    }

    @Test
    public void testGetDateAfter() throws Exception {
        System.out.println(DateUtil.getHourBefore(new Date(), 1));
    }

    @Test
    public void testFormatDateTime() throws Exception {
        System.out.println(DateUtil.formatDateTime(new Date()));
    }

    @Test
    public void testFormatDate() throws Exception {

    }

    @Test
    public void testString2Date() throws Exception {

    }

    @Test
    public void testIsExpire() throws Exception {

    }

    @Test
    public void testAfterMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -10);// 24小时制
        System.out.println(DateUtil.formatDateTime(calendar.getTime()));
    }
}