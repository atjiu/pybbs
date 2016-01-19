package com.jfinalbbs.notification;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class Notification extends Model<Notification> {

    public static final Notification me = new Notification();

    //查询未读消息
    public Page<Notification> paginate(int pageNumber, int pageSize, String authorId) {
        return super.paginate(pageNumber, pageSize, "select n.*, t.title, u.nickname ",
                "from notification n " +
                "left join topic t on n.tid = t.id " +
                "left join user u on u.id = n.from_author_id " +
                "where n.read = 1 and n.author_id = ? order by n.in_time desc", authorId);
    }

    //查询已读消息
    public List<Notification> findReadByAuthorId(String authorId, int size) {
        return super.find("select n.*, t.title, u.nickname from notification n " +
                "left join topic t on n.tid = t.id " +
                "left join user u on u.id = n.from_author_id " +
                "where n.read = 1 and n.author_id = ? order by n.in_time desc limit 0, ?", authorId, size);
    }

    //查询未读消息
    public List<Notification> findNotReadByAuthorId(String authorId) {
        return super.find("select n.*, t.title, u.nickname from notification n " +
                "left join topic t on n.tid = t.id " +
                "left join user u on u.id = n.from_author_id " +
                "where n.read = 0 and n.author_id = ? order by n.in_time desc", authorId);
    }

    //将未读消息设置成已读
    public int updateNotification(String uid) {
        return Db.update("update notification n set n.read = 1 where n.author_id = ?", uid);
    }

    //获取未读消息的数量
    public int countNotRead(String uid) {
        return super.find("select n.id as not_read_count from notification n where n.read = 0 and n.author_id = ?", uid).size();
    }
}
