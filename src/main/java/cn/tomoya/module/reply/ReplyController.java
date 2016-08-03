package cn.tomoya.module.reply;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.common.Constants.CacheEnum;
import cn.tomoya.interceptor.PermissionInterceptor;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.interceptor.UserStatusInterceptor;
import cn.tomoya.module.notification.Notification;
import cn.tomoya.module.notification.NotificationEnum;
import cn.tomoya.module.topic.Topic;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerBind(controllerKey = "/r", viewPath = "WEB-INF/page")
public class ReplyController extends BaseController {

    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void save() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if(method.equals("GET")) {
            renderError(404);
        } else if(method.equals("POST")) {
            String content = getPara("content");
            Integer tid = getParaToInt("tid");
            if(tid == null) {
                renderText(Constants.OP_ERROR_MESSAGE);
            } else {
                Date now = new Date();
                User user = getUser();
                Reply reply = new Reply();
                reply.set("tid", tid)
                        .set("content", content)
                        .set("in_time", now)
                        .set("author", user.getStr("nickname"))
                        .set("isdelete", false)
                        .save();
                //topic reply_count++
                Topic topic = Topic.me.findById(tid);
                topic.set("reply_count", topic.getInt("reply_count") + 1)
                        .set("last_reply_time", now)
                        .set("last_reply_author", user.getStr("nickname"))
                        .update();
//                user.set("score", user.getInt("score") + 5).update();
                //发送通知
                //回复者与话题作者不是一个人的时候发送通知
                if(!user.getStr("nickname").equals(topic.getStr("author"))) {
                    Notification.me.sendNotification(
                            user.getStr("nickname"),
                            topic.getStr("author"),
                            NotificationEnum.REPLY.name(),
                            tid,
                            content
                    );
                }
                //检查回复内容里有没有at用户,有就发通知
                List<String> atUsers = StrUtil.fetchUsers(content);
                for(String u: atUsers) {
                    if(!u.equals(topic.getStr("author"))) {
                        User _user = User.me.findByNickname(u);
                        if (_user != null) {
                            Notification.me.sendNotification(
                                    user.getStr("nickname"),
                                    _user.getStr("nickname"),
                                    NotificationEnum.AT.name(),
                                    tid,
                                    content
                            );
                        }
                    }
                }
                //清理缓存，保持数据最新
                clearCache(CacheEnum.topic.name() + tid);
                clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
                clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
                redirect("/t/" + tid + "#reply" + reply.getInt("id"));
            }
        }
    }

    /**
     * 编辑回复
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void edit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        Reply reply = Reply.me.findById(id);
        if(method.equals("GET")) {
            Topic topic = Topic.me.findById(reply.getInt("tid"));
            setAttr("reply", reply);
            setAttr("topic", topic);
            render("reply/edit.ftl");
        } else if(method.equals("POST")) {
            String content = getPara("content");
            reply.set("content", content).update();
            redirect("/t/" + reply.getInt("tid") + "#reply" + id);
        }
    }

    /**
     * 删除回复
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class,
            Tx.class
    })
    public void delete() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        Reply reply = Reply.me.findById(id);
        Topic topic = Topic.me.findById(reply.getInt("tid"));
        topic.set("reply_count", topic.getInt("reply_count") - 1).update();
        Reply.me.deleteById(id);
        clearCache(CacheEnum.topic.name() + topic.getInt("id"));
        //用户积分计算
//        User user = User.me.findByNickname(reply.getStr("author"));
//        Integer score = user.getInt("score");
//        score = score > 7 ? score - 7 : 0;
//        user.set("score", score).update();
        //清理缓存
//        clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//        clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
        redirect("/t/" + topic.getInt("id"));
    }

    /**
     * 回复列表
     */
    @Before({
            UserInterceptor.class,
            PermissionInterceptor.class
    })
    public void list() {
        setAttr("page", Reply.me.findAll(getParaToInt("p", 1), PropKit.getInt("pageSize")));
        render("reply/list.ftl");
    }
}
