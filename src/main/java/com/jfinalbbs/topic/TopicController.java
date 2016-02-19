package com.jfinalbbs.topic;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinalbbs.collect.Collect;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.label.Label;
import com.jfinalbbs.label.LabelTopicId;
import com.jfinalbbs.reply.Reply;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class TopicController extends BaseController {

    public void index() {
        String id = getPara(0);
        Topic topic = Topic.me.findByIdWithUser(id);
        if (topic != null) {
            setAttr("topic", topic);
            //查询话题下的回复
            List<Reply> replies = Reply.me.findByTid(id);
            setAttr("replies", replies);
            //查询标签
            List<Label> labels = Label.me.findByTid(id);
            setAttr("labels", labels);
            //根据当前话题的标签查询相关话题
            if(labels.size() > 0) {
                List<Topic> xgTopics = Label.me.findByLabels(id, labels, 10);
                setAttr("xgTopics", xgTopics);
            }
            //查询收藏信息
            User user = getSessionAttr(Constants.USER_SESSION);
            if (user != null) {
                Collect collect = Collect.me.findByTidAndAuthorId(id, user.getStr("id"));
                setAttr("collect", collect);
            }
            //查询该话题被收藏的数量
            int collectCount = Collect.me.findByTid(id).size();
            setAttr("collectCount", collectCount);
            //查询该作者下的其他话题
            List<Topic> otherTopics = Topic.me.findByAuthorIdNotTid(topic.getStr("author_id"), id, 5);
            setAttr("otherTopics", otherTopics);
            //查询无人回复的话题
            List<Topic> notReplyTopics = Topic.me.findNotReply(5);
            setAttr("notReplyTopics", notReplyTopics);
            render("front/topic/index.ftl");
        } else {
            renderText("您查询的话题不存在");
        }
    }

    /**
     * 创建话题
     */
    @Before(UserInterceptor.class)
    public void create() {
        String labelName = getPara(0);
        if(!StrUtil.isBlank(labelName)) {
            Label label = Label.me.findByName(labelName);
            setAttr("label", label);
        }
        render("front/topic/create.ftl");
    }

    /**
     * 保存话题
     */
    @Before({UserInterceptor.class, Tx.class})
    public void save() {
        User sessionUser = getSessionAttr(Constants.USER_SESSION);
        User user = User.me.findById(sessionUser.get("id"));
        String sid = getPara("sid");
        String title = getPara("title");
        String content = getPara("content");
        String label = getPara("label");
        Date date = new Date();
        Topic topic = new Topic();
        String tid = StrUtil.getUUID();
        topic.set("id", tid)
                .set("in_time", date)
                .set("last_reply_time", date)
                .set("s_id", StrUtil.transHtml(sid))
                .set("title", StrUtil.transHtml(title))
                .set("content", content)
                .set("view", 0)
                .set("author_id", user.get("id"))
                .set("top", 0)
                .set("good", 0)
                .set("show_status", 1)
                .save();
        //处理标签
        if (!StrUtil.isBlank(label)) {
            label = StrUtil.transHtml(label);
            String[] labels = label.split(",");
            for (String l : labels) {
                Label label1 = Label.me.findByName(l);
                if (label1 == null) {
                    label1 = new Label();
                    label1.set("name", l)
                            .set("in_time", date)
                            .set("topic_count", 1)
                            .save();
                } else {
                    label1.set("topic_count", label1.getInt("topic_count") + 1).update();
                }
                LabelTopicId.me.save(label1.getInt("id"), tid);
            }
        }
        //将积分增加3分
        user.set("score", user.getInt("score") + 3).update();
        setSessionAttr(Constants.USER_SESSION, user);
        redirect(baseUrl() + "/topic/" + topic.get("id"));
    }

    @Before(UserInterceptor.class)
    public void edit() {
        String tid = getPara(0);
        Topic topic = Topic.me.findWithSection(tid);
        User user = getSessionAttr(Constants.USER_SESSION);
        if (topic == null || !topic.get("author_id").equals(user.get("id"))) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            topic.set("content", topic.getStr("content"));
            setAttr("topic", topic);
            //查询标签
            List<Label> labels = Label.me.findByTid(tid);
            setAttr("labels", labels);
            render("front/topic/edit.ftl");
        }
    }

    @Before(UserInterceptor.class)
    public void update() {
        String tid = getPara("tid");
        Topic topic = Topic.me.findById(tid);
        if (topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            String sid = getPara("sid");
            String title = getPara("title");
            String content = getPara("content");
            String label = getPara("label");
            Date date = new Date();
            topic.set("title", StrUtil.transHtml(title))
                    .set("s_id", StrUtil.transHtml(sid))
                    .set("content", content)
                    .set("modify_time", date)
                    .set("last_reply_time", date)
                    .update();
            //删除label_topic_id里所有该话题的标签关联数据
            LabelTopicId.me.deleteByTid(tid);
            //处理标签
            if (!StrUtil.isBlank(label)) {
                label = StrUtil.transHtml(label);
                String[] labels = label.split(",");
                for (String l : labels) {
                    Label label1 = Label.me.findByName(l);
                    if (label1 == null) {
                        label1 = new Label();
                        label1.set("name", l)
                                .set("in_time", date)
                                .set("topic_count", 0)
                                .save();
                    }
                    LabelTopicId.me.save(label1.getInt("id"), tid);
                }
            }
            redirect(baseUrl() + "/topic/" + tid);
        }
    }

    @Before({UserInterceptor.class, Tx.class})
    public void delete() {
        String tid = getPara(0);
        Topic topic = Topic.me.findById(tid);
        User user = getSessionAttr(Constants.USER_SESSION);
        if (topic == null || !topic.get("author_id").equals(user.get("id"))) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            //删除关联的标签
            LabelTopicId.me.deleteByTid(tid);
            topic.delete();
            //删除回复
            Reply.me.deleteByTid(topic.getStr("id"));
            //删除收藏
            Collect.me.deleteByTid(topic.getStr("id"));
            redirect(baseUrl() + "/");
        }
    }

}
