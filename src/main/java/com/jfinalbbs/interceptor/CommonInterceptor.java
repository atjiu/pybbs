package com.jfinalbbs.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.link.Link;
import com.jfinalbbs.section.Section;
import com.jfinalbbs.system.SysConfig;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.DateUtil;
import com.jfinalbbs.utils.StrUtil;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class CommonInterceptor implements Interceptor {

    public void intercept(Invocation ai) {
        // session cookie 互换
        String user_cookie = ai.getController().getCookie(Constants.USER_COOKIE);
        User user_session = (User) ai.getController().getSession().getAttribute(Constants.USER_SESSION);
        if(StrUtil.isBlank(user_cookie) && user_session != null) {
            ai.getController().setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user_session.getStr("token")), 30*24 * 60 * 60);
        } else if (!StrUtil.isBlank(user_cookie) && user_session == null) {
            User user = User.me.findByToken(StrUtil.getDecryptToken(user_cookie));
            ai.getController().setSessionAttr(Constants.USER_SESSION, user);
        }
        Controller controller = ai.getController();
        // 获取今天时间，放到session里
        controller.setSessionAttr("today", DateUtil.formatDate(new Date()));
        // 查询板块
        controller.setAttr("sections", Section.me.findShow());
        // 查询友链
        controller.setAttr("links", Link.me.findAll());
        controller.setAttr("baseUrl", Constants.getValue("baseUrl"));
        controller.setAttr("siteTitle", Constants.getValue("siteTitle"));
        ai.invoke();
    }
}
