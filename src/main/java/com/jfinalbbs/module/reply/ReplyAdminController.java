package com.jfinalbbs.module.reply;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.module.log.AdminLog;
import com.jfinalbbs.module.user.AdminUser;
import com.jfinalbbs.module.user.User;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/reply", viewPath = "page/admin/reply")
public class ReplyAdminController extends BaseController {

    @RequiresPermissions("menu:reply")
    public void index() {
        setAttr("page", Reply.me.page(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }

    @RequiresPermissions("reply:delete")
    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        try {
            Reply reply = Reply.me.findById(id);
            String content = reply.getStr("content");
            User user = User.me.findById(reply.get("author_id"));
            reply.set("id", id).set("content", "回复已被删除").set("isdelete", 1).update();
            if (user.getInt("score") <= 2) {
                user.set("score", 0).update();
            } else {
                user.set("score", user.getInt("score") - 2).update();
            }
            //日志记录
            AdminUser adminUser = getAdminUser();
            AdminLog adminLog = new AdminLog();
            adminLog.set("uid", adminUser.getInt("id"))
                    .set("target_id", id)
                    .set("source", "reply")
                    .set("in_time", new Date())
                    .set("action", "delete")
                    .set("message", content)
                    .save();
            success();
        } catch (Exception e) {
            e.printStackTrace();
            error(Constants.DELETE_FAILURE);
        }
    }
}
