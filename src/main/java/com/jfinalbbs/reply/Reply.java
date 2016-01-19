package com.jfinalbbs.reply;

import com.jfinalbbs.utils.DateUtil;
import com.jfinalbbs.utils.MarkdownUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Reply extends Model<Reply> {

    public static final Reply me = new Reply();

    public List<Reply> findByTid(String tid) {
        return find("select u.avatar, u.nickname, r.* " +
                "from reply r left join user u on r.author_id = u.id " +
                "where r.tid = ? order by r.in_time", tid);
    }

    public int deleteByTid(String tid) {
        return Db.update("delete from reply where tid = ?", tid);
    }

    //查询总回复数
    public int replyCount() {
        return super.find("select id from reply").size();
    }

    public Reply findBestReplyByTid(String tid) {
        return super.findFirst("select * from reply r where r.best = 1 and r.tid = ?", tid);
    }

    // ------- 后台查询方法 -------
    public Page<Reply> page(int pageNumber, int pageSize) {
        return super.paginate(pageNumber, pageSize, "select r.*, t.title, u.nickname ", "from reply r left join topic t on r.tid = t.id left join user u on r.author_id = u.id order by r.in_time desc");
    }

    public List<Reply> findToday() {
        String start = DateUtil.formatDate(new Date()) + " 00:00:00";
        String end = DateUtil.formatDate(new Date()) + " 23:59:59";
        return super.find("select r.id, r.tid, r.content, r.in_time, t.title, r.isdelete " +
                        "from reply r left join topic t on r.tid = t.id where r.in_time between ? and ? order by r.in_time desc",
                        start, end);
    }

    //markdown语法转html
    public String md2html(String content) {
        return MarkdownUtil.marked(content);
    }

    public Page<Reply> pageByAuthorId(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, "select * ", " from reply r where r.author_id = ? group by r.tid", authorId);
    }
}
