package com.jfinalbbs.collect;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseModel;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Collect extends BaseModel<Collect> {

    public static final Collect me = new Collect();

    public Page<Collect> findByAuthorId(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, "select * ", "from jfbbs_collect where author_id = ?", authorId);
    }

    public List<Collect> findByTid(String tid) {
        return super.find("select * from jfbbs_collect where tid = ?", tid);
    }

    public Collect findByTidAndAuthorId(String tid, String authorId) {
        return super.findFirst("select * from jfbbs_collect where tid = ? and author_id = ?", tid, authorId);
    }

    public boolean deleteByTidAndAuthorId(String tid, String authorId) {
        Collect collect = findByTidAndAuthorId(tid, authorId);
        return super.deleteById(collect.get("id"));
    }

    public Page<Collect> findByAuthorIdWithTopic(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, " select t.*, s.name as sectionName, s.tab, u.avatar, " +
                        " (select u.avatar from jfbbs_user u where u.id = t.last_reply_author_id) as last_reply_author_avatar ",
                " from jfbbs_collect c left join jfbbs_topic t on c.tid = t.id " +
                        " left join jfbbs_section s on s.id = t.s_id " +
                        " left join jfbbs_user u on u.id = t.author_id " +
                        " where c.author_id = ?", authorId);
    }

    public List<Collect> findByAuthorIdWithTopic(String authorId) {
        return super.find("select t.*, s.name as sectionName, s.tab, u.avatar, u.nickname" +
                " from jfbbs_collect c left join jfbbs_topic t on c.tid = t.id " +
                " left join jfbbs_section s on s.id = t.s_id " +
                " left join jfbbs_user u on u.id = t.author_id " +
                " where c.author_id = ?", authorId);
    }

    public int deleteByTid(String tid) {
        return Db.update("delete from jfbbs_collect where tid = ?", tid);
    }

}
