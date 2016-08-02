package cn.tomoya.interceptor;

import cn.tomoya.common.Constants;
import cn.tomoya.module.notification.Notification;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class CommonInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        PropKit.use("config.properties");

        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        if(StrUtil.notBlank(user_cookie)) {
            String user_access_token = StrUtil.getDecryptToken(user_cookie);
            User user = User.me.findByAccessToken(user_access_token);
            if(user == null) {
                controller.removeCookie(Constants.USER_ACCESS_TOKEN, "/", PropKit.get("cookie.domain"));
            } else {
                int count = Notification.me.findNotReadCount(user.getStr("nickname"));
                controller.setAttr("notifications", count == 0 ? null : count);
                controller.setAttr("userinfo", user);
            }
        }

        //如果是微博登录的话,要在页面头部添加meta标签
        String loginChannel = PropKit.get("login.channel");
        Map<String, String> loginChannelMap = new HashMap<String, String>();
        if(loginChannel.equals(Constants.LoginEnum.Weibo.name())) {
            loginChannelMap.put("loginChannelName", Constants.LoginEnum.Weibo.name());
            loginChannelMap.put("loginChannelUrl", "/oauth/weibologin");
            controller.setAttr("login_channel", loginChannelMap);
            controller.setAttr("weibometa", PropKit.get("weibo.meta"));
        } else if(StrUtil.isBlank(loginChannel) || loginChannel.equals(Constants.LoginEnum.Github.name())) {
            loginChannelMap.put("loginChannelName", Constants.LoginEnum.Github.name());
            loginChannelMap.put("loginChannelUrl", "/oauth/githublogin");
            controller.setAttr("login_channel", loginChannelMap);
        }
        controller.setAttr("solrStatus", PropKit.getBoolean("solr.status")?"true":"false");
        controller.setAttr("shareDomain", PropKit.get("share.domain"));
        controller.setAttr("siteTitle", PropKit.get("siteTitle"));
        controller.setAttr("beianName", PropKit.get("beianName"));
        controller.setAttr("tongjiJs", PropKit.get("tongjiJs"));
        inv.invoke();
    }
}
