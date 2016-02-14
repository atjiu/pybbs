package com.jfinalbbs.message;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.notification.Notification;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;

import java.util.Date;
import java.util.List;

import static com.jfinalbbs.message.MsgContact.me;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(UserInterceptor.class)
public class MsgMessageController extends BaseController {

    /**
     * 查询会话列表
     */
    public void index() {
        User user = getSessionAttr(Constants.USER_SESSION);
        if (user != null) {
            List<MsgContact> msgContacts = me.findByAuthorId(user.getStr("id"));
            setAttr("msgContacts", msgContacts);
            render("front/message/index.ftl");
        } else {
            redirect(baseUrl());
        }
    }

    public void read() {
        Integer contactId = getParaToInt(0);
        if (contactId != null) {
            MsgContact msgContact = MsgContact.me.findById(contactId);
            User toUser = User.me.findById(msgContact.get("to_author_id"));
            setAttr("toUser", toUser);
            List<Message> messages = Message.me.findByContactId(contactId);
            setAttr("messages", messages);
            render("front/message/read.ftl");
        }
    }

    @Before(Tx.class)
    public void save() {
        User user = getSessionAttr(Constants.USER_SESSION);
        if (user != null) {
            String method = getRequest().getMethod();
            if (method.equalsIgnoreCase(Constants.POST)) {
                Date date = new Date();
                String toAuthorId = getPara("toAuthorId");
                String messageContent = getPara("messageContent");
                Integer msgCount = 0;
                //保存会话记录
                MsgContact msgContact = me.findByAuthorIdAndToAuthorId(user.getStr("id"), toAuthorId);
                if (msgContact == null) {
                    msgContact = new MsgContact();
                    msgContact.set("author_id", user.getStr("id"))
                            .set("to_author_id", toAuthorId)
                            .set("in_time", date);
                } else {
                    msgCount = msgContact.getInt("msg_count");
                }
                msgContact.set("msg_count", msgCount + 1)
                        .set("last_msg_content", StrUtil.transHtml(messageContent))
                        .set("last_msg_time", date)
                        .set("is_delete", 0);
                if (msgContact.get("id") == null) {
                    msgContact.save();
                } else {
                    msgContact.update();
                }
                MsgContact msgContact1 = me.findByAuthorIdAndToAuthorId(toAuthorId, user.getStr("id"));
                if (msgContact1 == null) {
                    msgContact1 = new MsgContact();
                    msgContact1.set("author_id", toAuthorId)
                            .set("to_author_id", user.getStr("id"))
                            .set("in_time", date);
                } else {
                    msgCount = msgContact1.getInt("msg_count");
                }
                msgContact1.set("msg_count", msgCount + 1)
                        .set("last_msg_content", StrUtil.transHtml(messageContent))
                        .set("last_msg_time", date)
                        .set("is_delete", 0);
                if (msgContact1.get("id") == null) {
                    msgContact1.save();
                } else {
                    msgContact1.update();
                }
                //保存消息内容
                Message message = new Message();
                message.set("contact_id", msgContact.get("id"))
                        .set("content", StrUtil.transHtml(messageContent))
                        .set("author_id", user.getStr("id"))
                        .set("in_time", date)
                        .save();
                Message message1 = new Message();
                message1.set("contact_id", msgContact1.get("id"))
                        .set("content", StrUtil.transHtml(messageContent))
                        .set("author_id", user.getStr("id"))
                        .set("in_time", date)
                        .save();
                //发送通知
                Notification notification = new Notification();
                notification.set("read", 0)
                        .set("message", Constants.NOTIFICATION_PRIVATE_MESSAGE)
                        .set("from_author_id", user.getStr("id"))
                        .set("author_id", toAuthorId)
                        .set("in_time", date).save();
                redirect(baseUrl() + "/message");
            }
        }
    }

    @Before(Tx.class)
    public void delete() {
        Integer contactId = getParaToInt(0);
        if (contactId != null) {
            MsgContact msgContact = me.findById(contactId);
            if(msgContact != null) {
                msgContact.set("is_delete", 1).update();
                redirect(baseUrl() + "/message");
            }
        }
    }
}
