package com.jfinalbbs.reply;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseModel;
import com.jfinalbbs.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Reply extends BaseModel<Reply> {

    public static final Reply me = new Reply();

    public List<Reply> findByTid(String tid) {
        return find("select u.avatar, u.nickname, r.* " +
                "from jfbbs_reply r left join jfbbs_user u on r.author_id = u.id " +
                "where r.tid = ? order by r.in_time", tid);
    }

    public int deleteByTid(String tid) {
        return Db.update("delete from jfbbs_reply where tid = ?", tid);
    }

    //查询总回复数
    public int replyCount() {
        return super.find("select id from jfbbs_reply").size();
    }

    public Reply findBestReplyByTid(String tid) {
        return super.findFirst("select * from jfbbs_reply r where r.best = 1 and r.tid = ?", tid);
    }

    // ------- 后台查询方法 -------
    public Page<Reply> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select r.*, t.title, u.nickname ",
                "from jfbbs_reply r left join jfbbs_topic t on r.tid = t.id " +
                        "left join jfbbs_user u on r.author_id = u.id order by r.in_time desc");
    }

    public List<Reply> findToday() {
        String start = DateUtil.formatDate(new Date()) + " 00:00:00";
        String end = DateUtil.formatDate(new Date()) + " 23:59:59";
        return super.find("select r.id, r.tid, r.content, r.in_time, t.title, r.isdelete " +
                        "from jfbbs_reply r left join jfbbs_topic t on r.tid = t.id where r.in_time between ? and ? order by r.in_time desc",
                start, end);
    }

}
