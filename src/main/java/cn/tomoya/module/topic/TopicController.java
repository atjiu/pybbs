package cn.tomoya.module.topic;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.common.Constants.CacheEnum;
import cn.tomoya.interceptor.PermissionInterceptor;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.interceptor.UserStatusInterceptor;
import cn.tomoya.module.collect.Collect;
import cn.tomoya.module.reply.Reply;
import cn.tomoya.module.section.Section;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.SolrUtil;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerBind(controllerKey = "/t", viewPath = "WEB-INF/page")
public class TopicController extends BaseController {

    /**
     * 话题详情
     */
    public void index() throws UnsupportedEncodingException {
        Integer tid = getParaToInt(0);
        Topic topic = Topic.me.findById(tid);
        if (topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            //处理一下置顶，精华
            topic.put("_top", topic.getBoolean("top") ? "取消置顶" : "置顶");
            topic.put("_good", topic.getBoolean("good") ? "取消加精" : "加精");
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
            //查询版块名称
            Section section = Section.me.findByTab(topic.getStr("tab"));
            //查询话题作者信息
            User authorinfo = User.me.findByNickname(topic.getStr("author"));
            //查询作者其他话题
            List<Topic> otherTopics = Topic.me.findOtherTopicByAuthor(tid, topic.getStr("author"), 7);
            //查询回复
            Page<Reply> page = Reply.me.page(getParaToInt("p"), PropKit.getInt("replyPageSize"), tid);
            //查询收藏数量
            long collectCount = Collect.me.countByTid(tid);
            //查询当前用户是否收藏了该话题
            User user = getUser();
            if (user != null) {
                Collect collect = Collect.me.findByTidAndUid(tid, user.getInt("id"));
                setAttr("collect", collect);
            }
            setAttr("topic", topic);
            setAttr("topicAppends", topicAppends);
            setAttr("section", section);
            setAttr("authorinfo", authorinfo);
            setAttr("otherTopics", otherTopics);
            setAttr("page", page);
            setAttr("collectCount", collectCount);
            render("topic/detail.ftl");
        }
    }

    /**
     * 创建话题
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void create() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("sections", Section.me.findByShowStatus(true));
            render("topic/create.ftl");
        } else if (method.equals("POST")) {
            Date now = new Date();
            String title = getPara("title");
            String content = getPara("content");
            if (StrUtil.isBlank(Jsoup.clean(title, Whitelist.basic()))) {
                renderText(Constants.OP_ERROR_MESSAGE);
            } else {
                String tab = getPara("tab");
                User user = getUser();
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
                //给用户加分
//                user.set("score", user.getInt("score") + 5).update();
                //清理用户缓存
                clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
                clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
                redirect("/t/" + topic.getInt("id"));
            }
        }
    }

    /**
     * 编辑话题
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void edit() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        Topic topic = Topic.me.findById(id);
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("sections", Section.me.findByShowStatus(true));
            setAttr("topic", topic);
            render("topic/edit.ftl");
        } else if (method.equals("POST")) {
            String tab = getPara("tab");
            String title = getPara("title");
            String content = getPara("content");
            topic.set("tab", tab)
                    .set("title", Jsoup.clean(title, Whitelist.basic()))
                    .set("content", content)
                    .update();
            //索引话题
            if (PropKit.getBoolean("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            //清理缓存
            clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(topic.getStr("author"), "utf-8"));
            clearCache(CacheEnum.topic.name() + id);
            redirect("/t/" + topic.getInt("id"));
        }
    }

    /**
     * 话题追加
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void append() {
        String method = getRequest().getMethod();
        Integer tid = getParaToInt(0);
        Topic topic = Topic.me.findById(tid);
        User user = getUser();
        if (topic.getStr("author").equals(user.getStr("nickname"))) {
            if (method.equals("GET")) {
                setAttr("topic", topic);
                render("topic/append.ftl");
            } else if (method.equals("POST")) {
                Date now = new Date();
                String content = getPara("content");
                TopicAppend topicAppend = new TopicAppend();
                topicAppend.set("tid", tid)
                        .set("content", content)
                        .set("in_time", now)
                        .set("isdelete", false)
                        .save();
                //索引话题
                if (PropKit.getBoolean("solr.status")) {
                    topic.set("content", topic.getStr("content") + "\n" + content);
                    SolrUtil solrUtil = new SolrUtil();
                    solrUtil.indexTopic(topic);
                }
                //清理缓存
                clearCache(CacheEnum.topicappends.name() + tid);
                redirect("/t/" + tid);
            }
        } else {
            renderText(Constants.OP_ERROR_MESSAGE);
        }
    }

    /**
     * 编辑追加的内容
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void appendedit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        TopicAppend topicAppend = TopicAppend.me.findById(id);
        Topic topic = Topic.me.findById(topicAppend.getInt("tid"));
        if (method.equals("GET")) {
            setAttr("topicAppend", topicAppend);
            setAttr("topic", topic);
            render("topic/appendedit.ftl");
        } else if (method.equals("POST")) {
            String content = getPara("content");
            topicAppend.set("content", content)
                    .update();
            //索引话题
            if (PropKit.getBoolean("solr.status")) {
                topic.set("content", topic.getStr("content") + "\n" + content);
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            //清理缓存
            clearCache(CacheEnum.topicappends.name() + topic.getInt("id"));
            redirect("/t/" + topic.getInt("id"));
        }
    }

    /**
     * 删除话题
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class,
            Tx.class
    })
    public void delete() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            TopicAppend.me.deleteByTid(id);
            Reply.me.deleteByTid(id);
            Topic topic = Topic.me.findById(id);
            //删除用户积分
//            User user = User.me.findByNickname(topic.getStr("author"));
//            Integer score = user.getInt("score");
//            score = score > 7 ? score - 7 : 0;
//            user.set("score", score).update();
            //删除话题（非物理删除）
            Topic.me.deleteById(id);
            //删除索引
            if (PropKit.getBoolean("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexDelete(String.valueOf(id));
            }
            //清理缓存
//            clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//            clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
            redirect("/");
        }
    }

    /**
     * 置顶
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void top() {
        Integer id = getParaToInt("id");
        Topic.me.top(id);
        clearCache(CacheEnum.topic.name() + id);
        redirect("/t/" + id);
    }

    /**
     * 加精
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void good() {
        Integer id = getParaToInt("id");
        Topic.me.good(id);
        clearCache(CacheEnum.topic.name() + id);
        redirect("/t/" + id);
    }
}
