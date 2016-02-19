package com.jfinalbbs.topic;

import com.jfinal.aop.Before;
import com.jfinalbbs.collect.Collect;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.interceptor.ClientInterceptor;
import com.jfinalbbs.label.Label;
import com.jfinalbbs.label.LabelTopicId;
import com.jfinalbbs.reply.Reply;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class TopicClientController extends BaseController {

    /**
     * 查看话题
     */
    public void index() {
        Map<String, Object> map = new HashMap<String, Object>();
        String id = getPara(0);
        if (StrUtil.isBlank(id)) {
            error("话题id不能为空");
        } else {
            String token = getPara("token");
            if (!StrUtil.isBlank(token)) {
                User user = User.me.findByToken(token);
                if (user != null) {
                    Collect collect = Collect.me.findByTidAndAuthorId(id, user.getStr("id"));
                    map.put("collect", collect);
                }
            }
            Topic topic = Topic.me.findByIdWithUser(id);
            List<Reply> replies = Reply.me.findByTid(id);
            //查询该话题被收藏的数量
            int collectCount = Collect.me.findByTid(id).size();
            map.put("topic", topic);
            map.put("replies", replies);
            map.put("collectCount", collectCount);
            success(map);
        }
    }

    /**
     * 保存话题
     */
    @Before(ClientInterceptor.class)
    public void create() {
        String token = getPara("token");
        User user = getUser(token);
        Integer sid = getParaToInt("sid");
        String title = getPara("title");
        String label = getPara("label");
        String content = getPara("content");
        if (sid == null || StrUtil.isBlank(title) || StrUtil.isBlank(content)) {
            error("请完善话题信息");
        } else {
            Date date = new Date();
            String tid = StrUtil.getUUID();
            Topic topic = new Topic();
            topic.set("id", tid)
                    .set("in_time", date)
                    .set("s_id", sid)
                    .set("title", title)
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
            success(topic);
        }
    }

    /**
     * 删除话题
     */
    @Before(ClientInterceptor.class)
    public void delete() {
        String tid = getPara("tid");
        String token = getPara("token");
        User user = getUser(token);
        if(StrUtil.isBlank(tid)) {
            error("话题id不能为空");
        } else {
            Topic topic = Topic.me.findById(tid);
            if(topic == null) {
                error("话题不存在");
            } else {
                if(user.get("id").equals(topic.get("author_id"))) {
                    //删除关联的标签
                    LabelTopicId.me.deleteByTid(tid);
                    topic.delete();
                    //删除回复
                    Reply.me.deleteByTid(topic.getStr("id"));
                    //删除收藏
                    Collect.me.deleteByTid(topic.getStr("id"));
                    success();
                } else {
                    error("不能删除别人的话题");
                }
            }
        }
    }
}
