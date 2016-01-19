package com.jfinalbbs.mission;

import com.jfinalbbs.utils.DateUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Mission extends Model<Mission> {
    public static final Mission me = new Mission();

    public Mission findLastByAuthorId(String author_id) {
        return findFirst("select * from mission where author_id = ? order by in_time desc", author_id);
    }

    public Mission findByInTime(String author_id, String startDate, String endDate) {
        return findFirst("select * from mission m " +
                "where m.author_id = ? and (m.in_time between ? and ?)", author_id, startDate, endDate);
    }

    //查询每日签到列表
    public Page<Mission> paginate(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select m.*, u.nickname, u.score as totalScore ",
                "from mission m left join user u on m.author_id = u.id order by m.in_time desc");
    }

    public List<Mission> findTop10() {
        String startTime = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE);
        String endTime = DateUtil.formatDateTime(new Date(), DateUtil.FORMAT_DATE) + " 23:59:59";
        return super.find("select m.id, m.author_id, u.score, m.in_time, max(m.day) as day, u.nickname, u.avatar " +
                "from mission m left join user u on u.id = m.author_id " +
                "where m.in_time between ? and ? group by m.author_id order by day desc limit 10", startTime, endTime);
    }

    public List<Mission> findToday() {
        String start = DateUtil.formatDate(new Date()) + " 00:00:00";
        String end = DateUtil.formatDate(new Date()) + " 23:59:59";
        return super.find("select u.nickname, m.score, u.score as totalScore, m.in_time, m.day " +
                " from mission m left join user u on m.author_id = u.id " +
                " where m.in_time between ? and ? order by m.in_time desc", start, end);
    }
}
