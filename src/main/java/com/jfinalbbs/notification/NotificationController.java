package com.jfinalbbs.notification;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.user.User;
import com.jfinal.aop.Before;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class NotificationController extends BaseController {

    @Before(UserInterceptor.class)
    public void countnotread() {
        User user = getSessionAttr(Constants.USER_SESSION);
        if(user == null) {
            error(Constants.ResultDesc.FAILURE);
        } else {
            try {
                int count = Notification.me.countNotRead(user.getStr("id"));
                success(count);
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.ResultDesc.FAILURE);
            }
        }
    }
}
