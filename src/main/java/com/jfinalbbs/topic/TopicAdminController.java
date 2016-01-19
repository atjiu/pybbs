package com.jfinalbbs.topic;

import com.jfinalbbs.collect.Collect;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinalbbs.label.Label;
import com.jfinalbbs.label.LabelTopicId;
import com.jfinalbbs.reply.Reply;
import com.jfinalbbs.section.Section;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(AdminUserInterceptor.class)
public class TopicAdminController extends BaseController {

    public void index() {
        setAttr("page", Topic.me.page(getParaToInt("p", 1), PropKit.use("config.properties").getInt("page_size")));
        render("index.html");
    }

    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        Topic topic = Topic.me.findById(id);
        String author_id = topic.get("author_id");
        //删除关联的标签
        LabelTopicId.me.deleteByTid(id);
        Topic.me.deleteById(id);
        //删除回复
        Reply.me.deleteByTid(id);
        //删除收藏
        Collect.me.deleteByTid(id);
        //扣除积分
        User user = User.me.findById(author_id);
        if(user.getInt("score") <= 5) {
            user.set("score", 0).update();
        } else {
            user.set("score", user.getInt("score") - 5).update();
        }
        success();
    }

    public void top() {
        String id = getPara("id");
        if(StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if(topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("top", topic.getInt("top") == 1?0:1).update();
                success(topic);
            }
        }
    }

    public void good() {
        String id = getPara("id");
        if(StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if(topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("good", topic.getInt("good") == 1?0:1).update();
                success(topic);
            }
        }
    }

    public void show_status() {
        String id = getPara("id");
        if(StrUtil.isBlank(id)) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            Topic topic = Topic.me.findById(id);
            if(topic == null) {
                error(Constants.OP_ERROR_MESSAGE);
            } else {
                topic.set("show_status", topic.getInt("show_status") == 1?0:1).update();
                success(topic);
            }
        }
    }

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
                if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
                    setAttr("sections", Section.me.findShow());
                    Section topicTab = Section.me.findById(topic.get("s_id"));
                    setAttr("topic_tab", topicTab);
                    setAttr("topic", topic);
                    //查询标签
                    List<Label> labels = Label.me.findByTid(id);
                    setAttr("labels", labels);
                    render("edit.html");
                } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
                    String title = getPara("title");
                    String content = getPara("content");
                    String sid = getPara("sid");
                    String original_url = getPara("original_url");
                    topic.set("title", title)
                            .set("content", content)
                            .set("s_id", sid)
                            .set("original_url", original_url)
                            .set("reposted", StrUtil.isBlank(original_url) ? 0 : 1)
                            .set("modify_time", new Date())
                            .update();
                    getResponse().setCharacterEncoding("utf-8");
                    getResponse().getWriter().write("<script>alert('修改成功!');window.close();</script>");
                }
            }
        }
    }
}
