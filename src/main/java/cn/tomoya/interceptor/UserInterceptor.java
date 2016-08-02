package cn.tomoya.interceptor;

import cn.tomoya.common.Constants;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class UserInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        HttpServletRequest request = controller.getRequest();
        String user_cookie = controller.getCookie(Constants.USER_ACCESS_TOKEN);

        boolean flag = false;
        User user = null;
        if(StrUtil.notBlank(user_cookie)) {
            user = User.me.findByAccessToken(StrUtil.getDecryptToken(user_cookie));
            if(user != null) {
                flag = true;
            }
        }

        if(flag) {
            inv.invoke();
        } else {
            String querystring = request.getQueryString();
            String beforeUrl = request.getRequestURL() + "?" + querystring;
            if(StrUtil.isBlank(querystring)) {
                beforeUrl = request.getRequestURL().toString();
            }
            try {
                controller.redirect("/login?callback=" + URLEncoder.encode(beforeUrl, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}
