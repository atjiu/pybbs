package com.jfinalbbs.module.topic;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.module.collect.Collect;
import com.jfinalbbs.module.label.Label;
import com.jfinalbbs.module.label.LabelTopicId;
import com.jfinalbbs.module.log.AdminLog;
import com.jfinalbbs.module.reply.Reply;
import com.jfinalbbs.module.section.Section;
import com.jfinalbbs.module.user.AdminUser;
import com.jfinalbbs.module.user.User;
import com.jfinalbbs.utils.StrUtil;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/topic", viewPath = "page/admin/topic")
public class TopicAdminController extends BaseController {

    @RequiresPermissions("menu:topic")
    public void index() {
        setAttr("page", Topic.me.page(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }

    @RequiresPermissions("topic:delete")
    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        Topic topic = Topic.me.findById(id);
        String author_id = topic.get("author_id");
        //删除话题（非物理删除）
        topic.set("isdelete", 1).update();
        //删除关联的标签
        LabelTopicId.me.deleteByTid(id);
        //删除回复
        Reply.me.deleteByTid(id);
        //删除收藏
        Collect.me.deleteByTid(id);
        //扣除积分
        User user = User.me.findById(author_id);
        if (user.getInt("score") <= 5) {
            user.set("score", 0).update();
        } else {
            user.set("score", user.getInt("score") - 5).update();
        }
        //记录日志
        AdminUser adminUser = getAdminUser();
        AdminLog adminLog = new AdminLog();
        adminLog.set("uid", adminUser.getInt("id"))
                .set("target_id", id)
                .set("source", "topic")
                .set("in_time", new Date())
                .set("action", "delete")
                .set("message", null)
                .save();
        success();
    }

    @RequiresPermissions("topic:top")
    public void top() {
        String id = getPara("id");
        if (StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if (topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("top", topic.getInt("top") == 1 ? 0 : 1).update();
                //记录日志
                AdminUser adminUser = getAdminUser();
                AdminLog adminLog = new AdminLog();
                adminLog.set("uid", adminUser.getInt("id"))
                        .set("target_id", id)
                        .set("source", "topic")
                        .set("in_time", new Date())
                        .set("action", "top")
                        .set("message", null)
                        .save();
                success(topic);
            }
        }
    }

    @RequiresPermissions("topic:good")
    public void good() {
        String id = getPara("id");
        if (StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if (topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("good", topic.getInt("good") == 1 ? 0 : 1).update();
                //记录日志
                AdminUser adminUser = getAdminUser();
                AdminLog adminLog = new AdminLog();
                adminLog.set("uid", adminUser.getInt("id"))
                        .set("target_id", id)
                        .set("source", "topic")
                        .set("in_time", new Date())
                        .set("action", "good")
                        .set("message", null)
                        .save();
                success(topic);
            }
        }
    }

    @RequiresPermissions("topic:show_status")
    public void show_status() {
        String id = getPara("id");
        if (StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if (topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("show_status", topic.getInt("show_status") == 1 ? 0 : 1).update();
                //记录日志
                AdminUser adminUser = getAdminUser();
                AdminLog adminLog = new AdminLog();
                adminLog.set("uid", adminUser.getInt("id"))
                        .set("target_id", id)
                        .set("source", "topic")
                        .set("in_time", new Date())
                        .set("action", "show_status")
                        .set("message", null)
                        .save();
                success(topic);
            }
        }
    }

    @RequiresPermissions("topic:edit")
    public void edit() throws IOException {
        String method = getRequest().getMethod();
        String id = getPara(0);
        if (StrUtil.isBlank(id)) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if (topic == null) {
                renderText("话题不存在");
            } else {
                if (method.equalsIgnoreCase(Constants.GET)) {
                    setAttr("sections", Section.me.findShow());
                    Section topicTab = Section.me.findById(topic.get("s_id"));
                    setAttr("topic_tab", topicTab);
                    topic.set("content", topic.getStr("content"));
                    setAttr("topic", topic);
                    //查询标签
                    List<Label> labels = Label.me.findByTid(id);
                    setAttr("labels", labels);
                    render("edit.ftl");
                } else if (method.equalsIgnoreCase(Constants.POST)) {
                    String title = getPara("title");
                    String content = getPara("content");
                    String sid = getPara("sid");
                    topic.set("title", title)
                            .set("content", content)
                            .set("s_id", sid)
                            .set("modify_time", new Date())
                            .update();
//                    //记录日志
//                    AdminUser adminUser = getAdminUser();
//                    AdminLog adminLog = new AdminLog();
//                    adminLog.set("uid", adminUser.getInt("id"))
//                            .set("target_id", id)
//                            .set("source", "topic")
//                            .set("in_time", new Date())
//                            .set("action", "edit")
//                            .set("message", null)
//                            .save();
                    getResponse().setCharacterEncoding("utf-8");
                    getResponse().getWriter().write("<script>alert('修改成功!');window.close();</script>");
                }
            }
        }
    }
}
