package com.jfinalbbs.reply;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.notification.Notification;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;

import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class ReplyController extends BaseController {

    @Before(UserInterceptor.class)
    public void index() {
        String tid = getPara(0);
        Date date = new Date();
        Topic topic = Topic.me.findById(tid);
        User sessionUser = getSessionAttr(Constants.USER_SESSION);
        User user = User.me.findById(sessionUser.get("id"));
        if (topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            // 回复数量+1
            topic.set("reply_count", topic.getInt("reply_count") + 1).update();
            // 增加1积分
            Reply reply = new Reply();
            String content = getPara("content");
            reply.set("id", StrUtil.getUUID())
                    .set("tid", tid)
                    .set("content", content)
                    .set("in_time", date)
                    .set("author_id", user.get("id"));
            reply.save();
            //话题最后回复时间更新
            topic.set("last_reply_time", date).set("last_reply_author_id", user.get("id")).update();
            //用户积分增加
            user.set("score", user.getInt("score") + 1).update();
            setSessionAttr(Constants.USER_SESSION, user);
            List<String> ats = StrUtil.findAt(content);
            //发送通知
            boolean flag = false;
            if(ats.size() > 0) {
                for (String nickname: ats) {
                    User user1 = User.me.findByNickname(nickname);
                    if(user1 != null) {
                        //将@xx转换成链接
                        content = content.replace("@" + nickname, "[@" + nickname + "]("+Constants.getBaseUrl()+"/user/"+user1.getStr("id")+")");
                        if(!user1.getStr("id").equals(user.getStr("id"))) {
                            Notification collectNoti = new Notification();
                            collectNoti.set("tid", tid)
                                    .set("rid", reply.get("id"))
                                    .set("read", 0)
                                    .set("message", Constants.NOTIFICATION_MESSAGE)
                                    .set("from_author_id", user.get("id"))
                                    .set("author_id", user1.get("id"))
                                    .set("in_time", date).save();
                        }
                        if(user1.getStr("id").equals(topic.getStr("author_id"))) {
                            flag = true;
                        }
                    }
                }
            }
            //更新内容
            reply.set("content", content).update();
            //通知话题作者
            if(!user.getStr("id").equals(topic.getStr("author_id")) && !flag) {
                Notification collectNoti = new Notification();
                collectNoti.set("tid", tid)
                        .set("rid", reply.get("id"))
                        .set("read", 0)
                        .set("message", Constants.NOTIFICATION_MESSAGE1)
                        .set("from_author_id", user.get("id"))
                        .set("author_id", topic.get("id"))
                        .set("in_time", date).save();
            }
            redirect(Constants.getBaseUrl() + "/topic/" + tid + ".html" + "#" + reply.get("id"));
        }
    }

    @Before(UserInterceptor.class)
    public void best() {
        String tid = getPara("tid");
        String rid = getPara("rid");
        Reply reply = Reply.me.findBestReplyByTid(tid);
        if(reply != null) {
            error("该话题已经采纳最佳答案了,请不要重复采纳");
        } else {
            Reply reply1 = Reply.me.findById(rid);
            reply1.set("best", 1).update();
            success();
        }
    }
}
