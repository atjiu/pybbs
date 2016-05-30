package cn.tomoya.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import cn.tomoya.common.Constants;
import cn.tomoya.module.system.Permission;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.StrUtil;

import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
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
                controller.setAttr("userinfo", user);
            }
        }

        controller.setAttr("shareDomain", PropKit.get("share.domain"));
        controller.setAttr("siteTitle", PropKit.get("siteTitle"));
        controller.setAttr("beianName", PropKit.get("beianName"));
        controller.setAttr("tongjiJs", PropKit.get("tongjiJs"));
        inv.invoke();
    }
}
