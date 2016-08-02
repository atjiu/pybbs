package cn.tomoya.module.api;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.module.collect.Collect;
import cn.tomoya.module.reply.Reply;
import cn.tomoya.module.section.Section;
import cn.tomoya.module.topic.Topic;
import cn.tomoya.module.topic.TopicAppend;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerBind(controllerKey = "api")
public class ApiController extends BaseController {

    /**
     * 获取显示的版块列表
     */
    public void sections() {
        success(Section.me.findByShowStatus(true));
    }

    /**
     * 话题列表
     */
    public void topics() {
        String tab = getPara("tab");
        if (StrUtil.isBlank(tab)) {
            tab = "all";
        }
        Page<Topic> page = Topic.me.page(getParaToInt("p", 1), PropKit.getInt("pageSize", 20), tab);
        //处理数据
        for(Topic topic: page.getList()) {
            topic.remove("content", "isdelete", "show_status");
        }
        success(page);
    }

    /**
     * 话题详情
     * @throws UnsupportedEncodingException
     */
    public void t() throws UnsupportedEncodingException {
        Integer tid = getParaToInt(0);
        Boolean mdrender = getParaToBoolean("mdrender", true);
        Topic topic = Topic.me.findById(tid);
        if (topic == null) {
            error("话题不存在");
        } else {
            //查询追加内容
            List<TopicAppend> topicAppends = TopicAppend.me.findByTid(tid);
            //话题浏览次数+1
            topic.set("view", topic.getInt("view") + 1).update();
            //更新redis里的topic数据
            Cache cache = Redis.use();
            Topic _topic = cache.get(Constants.CacheEnum.topic.name() + tid);
            if (_topic != null) {
                _topic.set("view", _topic.getInt("view") + 1);
                cache.set(Constants.CacheEnum.topic.name() + tid, _topic);
            }
            //查询话题作者信息
            User authorinfo = User.me.findByNickname(topic.getStr("author"));
            authorinfo.remove("receive_msg", "isblock", "third_access_token", "third_id", "channel", "expire_time",
                    "access_token");
            //查询回复
            List<Reply> replies = Reply.me.findByTopicId(tid);
            //查询收藏数量
            long collectCount = Collect.me.countByTid(tid);

            //渲染markdown
            if(mdrender) {
                topic.set("content", topic.marked(topic.getStr("content")));
                for(TopicAppend ta: topicAppends) {
                    ta.set("content", ta.marked(ta.getStr("content")));
                }
                for(Reply reply: replies) {
                    reply.set("content", reply.marked(reply.getStr("content")));
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("topic", topic);
            map.put("topicAppends", topicAppends);
            map.put("authorinfo", authorinfo);
            map.put("replies", replies);
            map.put("collectCount", collectCount);
            success(map);
        }
    }

    /**
     * 用户主页
     * @throws UnsupportedEncodingException
     */
    public void user() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        Boolean mdrender = getParaToBoolean("mdrender", true);
        User currentUser = User.me.findByNickname(nickname);
        if (currentUser == null) {
            error("用户不存在");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("currentUser", currentUser);
            currentUser.remove("receive_msg", "isblock", "third_access_token", "third_id", "channel", "expire_time",
                    "access_token");
            Page<Topic> topicPage = Topic.me.pageByAuthor(1, 7, nickname);
            Page<Reply> replyPage = Reply.me.pageByAuthor(1, 7, nickname);
            //处理数据
            for(Topic topic: topicPage.getList()) {
                topic.remove("content", "isdelete", "show_status");
            }
            //渲染markdown
            if(mdrender) {
                for(Reply reply: replyPage.getList()) {
                    reply.set("content", reply.marked(reply.getStr("content")));
                }
            }
            map.put("topics", topicPage.getList());
            map.put("replies", replyPage.getList());
            success(map);
        }
    }
}
