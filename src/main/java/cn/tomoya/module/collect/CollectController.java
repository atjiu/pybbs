package cn.tomoya.module.collect;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.common.Constants.CacheEnum;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.module.notification.Notification;
import cn.tomoya.module.notification.NotificationEnum;
import cn.tomoya.module.topic.Topic;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;

import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerBind(controllerKey = "/collect", viewPath = "WEB-INF/page")
public class CollectController extends BaseController {

    /**
     * 收藏话题
     */
    @Before(UserInterceptor.class)
    public void add() {
        Integer tid = getParaToInt("tid");
        Date now = new Date();
        User user = getUser();
        Collect collect = new Collect();
        collect.set("tid", tid)
                .set("uid", user.getInt("id"))
                .set("in_time", now)
                .save();
        Topic topic = Topic.me.findById(tid);
        //创建通知
        Notification.me.sendNotification(
                user.getStr("nickname"),
                topic.getStr("author"),
                NotificationEnum.COLLECT.name(),
                tid,
                ""
        );
        //清理缓存
        clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
        clearCache(CacheEnum.collects.name() + user.getInt("id"));
        clearCache(CacheEnum.collectcount.name() + tid);
        clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
        redirect("/t/" + tid);
    }

    /**
     * 取消收藏话题
     */
    @Before(UserInterceptor.class)
    public void delete() {
        Integer tid = getParaToInt("tid");
        User user = getUser();
        Collect collect = Collect.me.findByTidAndUid(tid, user.getInt("id"));
        if(collect == null) {
            renderText("请先收藏");
        } else {
            collect.delete();
            clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
            clearCache(CacheEnum.collects.name() + user.getInt("id"));
            clearCache(CacheEnum.collectcount.name() + tid);
            clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
            redirect("/t/" + tid);
        }
    }
}
