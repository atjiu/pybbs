package cn.tomoya.module.user;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.common.Constants.CacheEnum;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.interceptor.UserStatusInterceptor;
import cn.tomoya.module.collect.Collect;
import cn.tomoya.module.reply.Reply;
import cn.tomoya.module.topic.Topic;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerBind(controllerKey = "/user", viewPath = "WEB-INF/page")
public class UserController extends BaseController {

    /**
     * 用户个人主页
     */
    public void index() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        if (StrUtil.isBlank(nickname)) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            User currentUser = User.me.findByNickname(nickname);
            if(currentUser != null) {
                Long collectCount = Collect.me.countByUid(currentUser.getInt("id"));
                currentUser.put("collectCount", collectCount);

                Page<Topic> topicPage = Topic.me.pageByAuthor(1, 7, nickname);
                Page<Reply> replyPage = Reply.me.pageByAuthor(1, 7, nickname);
                setAttr("topicPage", topicPage);
                setAttr("replyPage", replyPage);
                setAttr("pageTitle", currentUser.getStr("nickname") + " 个人主页");
            } else {
                setAttr("pageTitle", "用户未找到");
            }
            setAttr("currentUser", currentUser);
            render("user/info.ftl");
        }
    }

    /**
     * 用户的话题列表
     */
    public void topics() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        User user = User.me.findByNickname(nickname);
        if (user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("currentUser", user);
            Page<Topic> page = Topic.me.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), nickname);
            setAttr("page", page);
            render("user/topics.ftl");
        }
    }

    /**
     * 用户的回复列表
     */
    public void replies() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        User user = User.me.findByNickname(nickname);
        if (user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("currentUser", user);
            Page<Reply> page = Reply.me.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), nickname);
            setAttr("page", page);
            render("user/replies.ftl");
        }
    }

    /**
     * 用户的话题列表
     */
    public void collects() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        User user = User.me.findByNickname(nickname);
        if (user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("currentUser", user);
            Page<Collect> page = Collect.me.findByUid(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getInt("id"));
            setAttr("page", page);
            render("user/collects.ftl");
        }
    }

    /**
     * 用户设置
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void setting() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if (method.equals("POST")) {
            String url = getPara("url");
            String signature = getPara("signature");
            Integer receive_msg = getParaToInt("receive_msg", 0);
            User user = getUser();
            user.set("signature", StrUtil.notBlank(signature) ? Jsoup.clean(signature, Whitelist.basic()) : null)
                    .set("url", StrUtil.notBlank(url) ? Jsoup.clean(url, Whitelist.basic()) : null)
                    .set("receive_msg", receive_msg == 1)
                    .update();
            //清理缓存
            clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
            clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
            setAttr("msg", "保存成功。");
        }
        render("user/setting.ftl");
    }

}
