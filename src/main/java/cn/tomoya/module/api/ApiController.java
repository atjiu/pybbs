package cn.tomoya.module.api;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.common.Constants.CacheEnum;
import cn.tomoya.interceptor.ApiInterceptor;
import cn.tomoya.module.collect.Collect;
import cn.tomoya.module.notification.Notification;
import cn.tomoya.module.notification.NotificationEnum;
import cn.tomoya.module.reply.Reply;
import cn.tomoya.module.section.Section;
import cn.tomoya.module.topic.Topic;
import cn.tomoya.module.topic.TopicAppend;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.SolrUtil;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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
    public void topics() throws UnsupportedEncodingException {
        String tab = getPara("tab");
        if (StrUtil.isBlank(tab)) {
            tab = "all";
        }
        Page<Topic> page = Topic.me.page(getParaToInt("p", 1), PropKit.getInt("pageSize", 20), tab);
        //处理数据
        for (Topic topic : page.getList()) {
            User user = User.me.findByNickname(topic.getStr("author"));
            topic.put("avatar", user.getStr("avatar"));
            topic.remove("content", "isdelete", "show_status");
        }
        success(page);
    }

    /**
     * 话题详情
     *
     * @throws UnsupportedEncodingException
     */
    public void topic() throws UnsupportedEncodingException {
        Integer tid = getParaToInt(0);
        String token = getPara("token");
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
            Topic _topic = cache.get(CacheEnum.topic.name() + tid);
            if (_topic != null) {
                _topic.set("view", _topic.getInt("view") + 1);
                cache.set(CacheEnum.topic.name() + tid, _topic);
            }
            //查询话题作者信息
            User authorinfo = User.me.findByNickname(topic.getStr("author"));
            authorinfo.remove("receive_msg", "isblock", "third_access_token", "third_id", "channel", "expire_time",
                    "access_token");
            topic.put("avatar", authorinfo.getStr("avatar"));
            //查询回复
            List<Reply> replies = Reply.me.findByTopicId(tid);
            //查询收藏数量
            long collectCount = Collect.me.countByTid(tid);
            //如果有token,查询该话题是否被收藏
            boolean isCollect = false;
            User currentUser = getUserByToken();
            if(currentUser != null) {
                Collect collect = Collect.me.findByTidAndUid(tid, currentUser.getInt("id"));
                isCollect = collect != null;
            }

            //渲染markdown
            if (mdrender) {
                topic.set("content", topic.marked(topic.getStr("content")));
                for (TopicAppend ta : topicAppends) {
                    ta.set("content", ta.marked(ta.getStr("content")));
                }
                for (Reply reply : replies) {
                    reply.put("avatar", User.me.findByNickname(reply.get("author")).get("avatar"));
                    reply.set("content", reply.marked(reply.getStr("content")));
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("topic", topic);
            map.put("topicAppends", topicAppends);
            map.put("authorinfo", authorinfo);
            map.put("replies", replies);
            map.put("collectCount", collectCount);
            map.put("isCollect", isCollect);
            success(map);
        }
    }

    /**
     * 用户主页
     *
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
            List<Topic> topics = Topic.me.findByAuthor(nickname);
            List<Reply> replies = Reply.me.findByAuthor(nickname);
            //处理数据
            for (Topic topic : topics) {
                topic.put("avatar", currentUser.get("avatar"));
                topic.remove("content", "isdelete", "show_status");
            }
            //渲染markdown
            for (Reply reply : replies) {
                reply.put("avatar", User.me.findByNickname(reply.get("author")).get("avatar"));
                if (mdrender) {
                    reply.put("replyContent", reply.marked(reply.getStr("replyContent")));
                }
                reply.remove("content", "isdelete", "show_status");
            }
            map.put("topics", topics);
            map.put("replies", replies);
            success(map);
        }
    }

    /**
     * 验证token正确性
     */
    @Before(ApiInterceptor.class)
    public void accesstoken() {
        User user = getUserByToken();
        user.remove("receive_msg", "isblock", "third_access_token", "third_id", "channel", "expire_time",
                "access_token");
        success(user);
    }

    /**
     * 发布话题
     *
     * @throws UnsupportedEncodingException
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/topic/create")
    public void create() throws UnsupportedEncodingException {
        Date now = new Date();
        String title = getPara("title");
        String content = getPara("content");
        String tab = getPara("tab");
        if (StrUtil.isBlank(Jsoup.clean(title, Whitelist.basic()))) {
            error(Constants.OP_ERROR_MESSAGE);
        } else if (StrUtil.isBlank(tab)) {
            error("请选择板块");
        } else {
            User user = getUserByToken();
            Topic topic = new Topic();
            topic.set("title", Jsoup.clean(title, Whitelist.basic()))
                    .set("content", content)
                    .set("tab", tab)
                    .set("in_time", now)
                    .set("last_reply_time", now)
                    .set("view", 0)
                    .set("author", user.get("nickname"))
                    .set("top", false)
                    .set("good", false)
                    .set("show_status", true)
                    .set("reply_count", 0)
                    .set("isdelete", false)
                    .save();
            //索引话题
            if (PropKit.getBoolean("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            success(topic);
        }
    }

    /**
     * 收藏话题
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/topic/collect")
    public void collect() {
        Integer tid = getParaToInt("tid");
        if (tid == null) {
            error("话题ID不能为空");
        } else {
            Topic topic = Topic.me.findById(tid);
            if (topic == null) {
                error("收藏的话题不存在");
            } else {
                Date now = new Date();
                User user = getUserByToken();
                Collect collect = Collect.me.findByTidAndUid(tid, user.getInt("id"));
                if (collect == null) {
                    collect = new Collect();
                    collect.set("tid", tid)
                            .set("uid", user.getInt("id"))
                            .set("in_time", now)
                            .save();
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
                    success();
                } else {
                    error("你已经收藏了此话题");
                }
            }
        }
    }

    /**
     * 取消收藏
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/topic/del_collect")
    public void del_collect() {
        Integer tid = getParaToInt("tid");
        User user = getUserByToken();
        Collect collect = Collect.me.findByTidAndUid(tid, user.getInt("id"));
        if (collect == null) {
            error("请先收藏");
        } else {
            collect.delete();
            clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
            clearCache(CacheEnum.collects.name() + user.getInt("id"));
            clearCache(CacheEnum.collectcount.name() + tid);
            clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
            success();
        }
    }

    /**
     * 收藏话题
     */
    public void collects() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        Boolean mdrender = getParaToBoolean("mdrender", true);
        if (StrUtil.isBlank(nickname)) {
            error("用户昵称不能为空");
        } else {
            User user = User.me.findByNickname(nickname);
            if (user == null) {
                error("无效用户");
            } else {
                List<Collect> collects = Collect.me.findByUid(user.getInt("id"));
                for (Collect collect : collects) {
                    collect.put("avatar", User.me.findByNickname(collect.get("author")).get("avatar"));
                    if (mdrender) {
                        collect.put("content", collect.marked(collect.get("content")));
                    }
                }
                success(collects);
            }
        }
    }

    /**
     * 创建评论
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/reply/create")
    public void createReply() throws UnsupportedEncodingException {
        Integer tid = getParaToInt("tid");
        String content = getPara("content");
        if (tid == null || StrUtil.isBlank(content)) {
            error("话题ID和回复内容都不能为空");
        } else {
            Topic topic = Topic.me.findById(tid);
            if (topic == null) {
                error("话题不存在");
            } else {
                Date now = new Date();
                User user = getUserByToken();
                Reply reply = new Reply();
                reply.set("tid", tid)
                        .set("content", content)
                        .set("in_time", now)
                        .set("author", user.getStr("nickname"))
                        .set("isdelete", false)
                        .save();
                //topic reply_count++
                topic.set("reply_count", topic.getInt("reply_count") + 1)
                        .set("last_reply_time", now)
                        .set("last_reply_author", user.getStr("nickname"))
                        .update();
//                user.set("score", user.getInt("score") + 5).update();
                //发送通知
                //回复者与话题作者不是一个人的时候发送通知
                if (!user.getStr("nickname").equals(topic.getStr("author"))) {
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
                for (String u : atUsers) {
                    if (!u.equals(topic.getStr("author"))) {
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
                success(reply);
            }
        }
    }

    /**
     * 未读通知数
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/notification/count")
    public void msgCount() {
        User user = getUserByToken();
        int count = Notification.me.findNotReadCount(user.getStr("nickname"));
        success(count);
    }

    /**
     * 通知列表
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/notifications")
    public void notifications() throws UnsupportedEncodingException {
        Boolean mdrender = getParaToBoolean("mdrender", true);
        User user = getUserByToken();
        Page<Notification> page = Notification.me.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getStr("nickname"));
        for (Notification notification : page.getList()) {
            notification.put("avatar", User.me.findByNickname(notification.get("author")).get("avatar"));
            if (mdrender) {
                notification.set("content", notification.marked(notification.get("content")));
            }
        }
        //将通知都设置成已读的
        Notification.me.makeUnreadToRead(user.getStr("nickname"));
        success(page);
    }

}
