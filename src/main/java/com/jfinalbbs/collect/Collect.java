package com.jfinalbbs.collect;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Collect extends Model<Collect> {

    public static final Collect me = new Collect();

    public Page<Collect> findByAuthorId(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, "select * ", "from collect where author_id = ?", authorId);
    }

    public List<Collect> findByTid(String tid) {
        return super.find("select * from collect where tid = ?", tid);
    }

    public Collect findByTidAndAuthorId(String tid, String authorId) {
        return super.findFirst("select * from collect where tid = ? and author_id = ?", tid, authorId);
    }

    public boolean deleteByTidAndAuthorId(String tid, String authorId) {
        Collect collect = findByTidAndAuthorId(tid, authorId);
        return super.deleteById(collect.get("id"));
    }

    public Page<Collect> findByAuthorIdWithTopic(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, " select t.*, s.name as sectionName, s.tab, u.avatar, " +
                        " (select u.avatar from user u where u.id = t.last_reply_author_id) as last_reply_author_avatar, " +
                        " from collect c left join topic t on c.tid = t.id " +
                        " left join section s on s.id = t.s_id " +
                        " left join user u on u.id = t.author_id " +
                        " where c.author_id = ?", authorId);
    }

    public List<Collect> findByAuthorIdWithTopic(String authorId) {
        return super.find("select t.*, s.name as sectionName, s.tab, u.avatar, u.nickname" +
                " from collect c left join topic t on c.tid = t.id " +
                " left join section s on s.id = t.s_id " +
                " left join user u on u.id = t.author_id " +
                " where c.author_id = ?", authorId);
    }

    public int deleteByTid(String tid) {
        return Db.update("delete from collect where tid = ?", tid);
    }

}
