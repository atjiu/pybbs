package com.jfinalbbs.user;

import com.jfinalbbs.collect.Collect;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.notification.Notification;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.utils.AgentUtil;
import com.jfinalbbs.utils.ImageUtil;
import com.jfinalbbs.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class UserController extends BaseController {

    public void index() {
        String id = getPara(0);
        User user = User.me.findById(id);
        Integer day = User.me.findDayByAuthorId(id);
        if(user != null) {
            setAttr("current_user", user);
            setAttr("day", day);
            Page<Collect> collectPage = Collect.me.findByAuthorId(getParaToInt("p", 1),
                    PropKit.use("config.properties").getInt("page_size"), user.getStr("id"));
            setAttr("collectPage", collectPage);
            Page<Topic> topics = Topic.me.paginateByAuthorId(1, 5, user.getStr("id"));
            setAttr("topics", topics);
            //查询我回复的话题
            Page<Topic> myReplyTopics = Topic.me.paginateMyReplyTopics(1, 5, user.getStr("id"));
            setAttr("myReplyTopics", myReplyTopics);
            if(!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/user/index.html");
            else render("front/user/index.html");
        } else {
            renderText(Constants.OP_ERROR_MESSAGE);
        }
    }

    public void collects() {
        String uid = getPara(0);
        User user = User.me.findById(uid);
        if(user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Collect> collectPage = Collect.me.findByAuthorIdWithTopic(getParaToInt("p", 1),
                    PropKit.use("config.properties").getInt("page_size"), user.getStr("id"));
            setAttr("page", collectPage);
            render("front/user/collects.html");
        }
    }

    public void topics() {
        String uid = getPara(0);
        User user = User.me.findById(uid);
        if(user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Topic> page = Topic.me.paginateByAuthorId(getParaToInt("p", 1),
                    PropKit.use("config.properties").getInt("page_size"), user.getStr("id"));
            setAttr("page", page);
            render("front/user/topics.html");
        }
    }

    public void replies() {
        String uid = getPara(0);
        User user = User.me.findById(uid);
        if(user == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Topic> myReplyTopics = Topic.me.paginateMyReplyTopics(getParaToInt("p", 1), PropKit.use("config.properties").getInt("page_size"), user.getStr("id"));
            setAttr("page", myReplyTopics);
            render("front/user/replies.html");
        }
    }

    public void top100() {
        List<User> top100 = User.me.findBySize(100);
        setAttr("top100", top100);
        render("front/user/top100.html");
    }

    @Before(UserInterceptor.class)
    public void message() {
        String uid = getPara(0);
        if(StrUtil.isBlank(uid)) renderText(Constants.OP_ERROR_MESSAGE);
        List<Notification> notifications = Notification.me.findNotReadByAuthorId(uid);
        setAttr("notifications", notifications);
        Page<Notification> oldMessages = Notification.me.paginate(getParaToInt("p", 1),
                PropKit.use("config.properties").getInt("page_size"), uid);
        setAttr("oldMessages", oldMessages);
        //将消息置为已读
        Notification.me.updateNotification(uid);
        render("front/user/message.html");
    }

    @Before(UserInterceptor.class)
    public void setting() throws InterruptedException {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            setAttr("save", getPara("save"));
            render("front/user/setting.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            User user = getSessionAttr(Constants.USER_SESSION);
            String url = getPara("url");
            String nickname = getPara("nickname");
            if(!user.getStr("nickname").equalsIgnoreCase(nickname) && User.me.findByNickname(nickname) != null) {
                error("此昵称已被注册,请更换昵称");
            } else {
                if(!StrUtil.isBlank(url)) {
                    if (!url.substring(0, 7).equalsIgnoreCase("http://")) {
                        url = "http://" + url;
                    }
                }
                user.set("url", StrUtil.transHtml(url))
                        .set("nickname", StrUtil.noHtml(nickname).trim())
                        .set("signature", StrUtil.transHtml(getPara("signature"))).update();
                //保存成功
                setSessionAttr(Constants.USER_SESSION, user);
                success();
            }
        }
    }

    @Before(UserInterceptor.class)
    public void cancelBind() {
        String pt = getPara("pt");
        if(StrUtil.isBlank(pt)) {
            error("非法操作");
        } else {
            User user = (User) getSession().getAttribute(Constants.USER_SESSION);
            if(pt.equalsIgnoreCase(Constants.ThirdLogin.QQ)) {
                user.set("qq_open_id", null)
                        .set("qq_avatar", null)
                        .set("qq_nickname", null)
                        .update();
            } else if(pt.equalsIgnoreCase(Constants.ThirdLogin.SINA)) {
                user.set("sina_open_id", null)
                        .set("sina_avatar", null)
                        .set("sina_nickname", null)
                        .update();
            }/* else if(pt.equalsIgnoreCase(Constants.ThirdLogin.WECHAT)) {
                user.set("wechat_open_id", null)
                        .set("wechat_avatar", null)
                        .update();
            }*/
            setSessionAttr(Constants.USER_SESSION, user);
            setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
            success();
        }
    }

    @Before(UserInterceptor.class)
    public void uploadAvatar() throws Exception {
        UploadFile uploadFile = getFile("avatar", Constants.UPLOAD_DIR_AVATAR);
        String path = Constants.getBaseUrl() + "/" + Constants.UPLOAD_DIR + "/" + Constants.UPLOAD_DIR_AVATAR + "/" + uploadFile.getFileName();
        User user = (User) getSession().getAttribute(Constants.USER_SESSION);
        user.set("avatar", path).update();
        //裁剪图片
        String realPath = PathKit.getWebRootPath() + "/static/upload/avatar/" + uploadFile.getFileName();
        System.out.println(realPath);
        ImageUtil.zoomImage(realPath, realPath, 100, 100);
        redirect(Constants.getBaseUrl() + "/user/setting");
    }

    public void testRootPath() {
        System.out.println(PathKit.getWebRootPath());
    }
}
