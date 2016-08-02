package cn.tomoya.module.notification;

import cn.tomoya.common.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class Notification extends BaseModel<Notification> {

    public static final Notification me = new Notification();

    /**
     * 查询未读通知数量
     * @param author
     * @return
     */
    public int findNotReadCount(String author) {
        return super.find(
                "select id from pybbs_notification where `read` = ? and target_author = ?",
                false,
                author
            ).size();
    }

    /**
     * 查询通知列表
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    public Page<Notification> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        return super.paginate(
                pageNumber,
                pageSize,
                "select n.*, t.title ",
                "from pybbs_notification n, pybbs_topic t where n.tid = t.id " +
                        "and n.target_author = ? order by n.read, n.in_time desc",
                author
            );
    }

    /**
     * 将用户的通知都置为已读
     * @param author
     */
    public void makeUnreadToRead(String author) {
        Db.update("update pybbs_notification set `read` = ? where `read` = ? and target_author = ?", true, false, author);
    }

    /**
     * 判断通知是否已读
     * @param notification
     * @return
     */
    public String isRead(Notification notification) {
        if(notification.getBoolean("read")) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 启动线程发送通知
     * @param author
     * @param targetAuthor
     * @param action
     * @param tid
     * @param content
     */
    public void sendNotification(String author, String targetAuthor, String action, Integer tid, String content) {
        new Thread(() -> {
            Notification notification = new Notification();
            notification.set("read", false)
                    .set("author", author)
                    .set("target_author", targetAuthor)
                    .set("in_time", new Date())
                    .set("action", action)
                    .set("tid", tid)
                    .set("content", content)
                    .save();
        }).start();
    }
}
