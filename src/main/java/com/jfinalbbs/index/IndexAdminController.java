package com.jfinalbbs.index;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinalbbs.mission.Mission;
import com.jfinalbbs.reply.Reply;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.user.User;
import com.jfinal.aop.Before;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(AdminUserInterceptor.class)
public class IndexAdminController extends BaseController {

    public void index() {
        //今日话题
        List<Topic> topics = Topic.me.findToday();
        setAttr("topics", topics);
        //今日回复
        List<Reply> replies = Reply.me.findToday();
        setAttr("replies", replies);
        //今日签到
        List<Mission> missions = Mission.me.findToday();
        setAttr("missions", missions);
        //今日用户
        List<User> users = User.me.findToday();
        setAttr("users", users);
        render("index.html");
    }

    public void logout() {
        removeSessionAttr(Constants.SESSION_ADMIN_USER);
        removeCookie(Constants.COOKIE_ADMIN_TOKEN);
        redirect(Constants.getBaseUrl() + "/");
    }
}