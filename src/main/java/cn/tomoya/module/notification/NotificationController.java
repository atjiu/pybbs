package cn.tomoya.module.notification;

import cn.tomoya.common.BaseController;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ControllerBind(controllerKey = "/notification", viewPath = "WEB-INF/page")
public class NotificationController extends BaseController {

    @Before(UserInterceptor.class)
    public void index() {
        User user = getUser();
        Page<Notification> page = Notification.me.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getStr("nickname"));
        //将通知都设置成已读的
        Notification.me.makeUnreadToRead(user.getStr("nickname"));
        setAttr("page", page);
        render("notification/index.ftl");
    }
}
