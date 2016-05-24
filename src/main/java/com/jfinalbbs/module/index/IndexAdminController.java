package com.jfinalbbs.module.index;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.module.mission.Mission;
import com.jfinalbbs.module.reply.Reply;
import com.jfinalbbs.module.topic.Topic;
import com.jfinalbbs.module.user.User;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin", viewPath = "page/admin")
public class IndexAdminController extends BaseController {

    @RequiresPermissions("menu:index")
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
        render("index.ftl");
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
        redirect("/adminlogin");
    }
}