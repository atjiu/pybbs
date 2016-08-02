package cn.tomoya.interceptor;

import cn.tomoya.common.Constants;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class UserStatusInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        User user = User.me.findByAccessToken(StrUtil.getDecryptToken(user_cookie));
        if(user.getBoolean("isblock")) {
            controller.renderText("您的账户已被禁用!");
        } else {
            inv.invoke();
        }

    }

}
