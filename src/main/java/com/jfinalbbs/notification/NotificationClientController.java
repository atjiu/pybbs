package com.jfinalbbs.notification;

import com.jfinal.aop.Before;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.interceptor.ClientInterceptor;
import com.jfinalbbs.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(ClientInterceptor.class)
public class NotificationClientController extends BaseController {

    public void countnotread() {
        String token = getPara("token");
        User user = getUser(token);
        int count = Notification.me.countNotRead(user.getStr("id"));
        success(count);
    }

    public void index() {
        String token = getPara("token");
        User user = getUser(token);
        List<Notification> notifications = Notification.me.findNotReadByAuthorId(user.getStr("id"));
        List<Notification> oldMessages = Notification.me.findReadByAuthorId(user.getStr("id"), 20);
        //将消息置为已读
        Notification.me.updateNotification(user.getStr("id"));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("notifications", notifications);
        map.put("oldMessages", oldMessages);
        success(map);
    }
}
