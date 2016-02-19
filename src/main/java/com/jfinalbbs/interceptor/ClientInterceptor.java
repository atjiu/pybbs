package com.jfinalbbs.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.Result;
import com.jfinalbbs.utils.StrUtil;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class ClientInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        String token = inv.getController().getPara("token");
        Result result = new Result(Constants.CODE_SUCCESS, Constants.DESC_SUCCESS, null);
        if(StrUtil.isBlank(token)) {
            result.setCode(Constants.CODE_FAILURE);
            result.setDescription("用户令牌不能为空");
            inv.getController().renderJson(result);
        } else {
            //验证token 的合法性
            User user = User.me.findByToken(token);
            if(user == null) {
                result.setCode(Constants.CODE_FAILURE);
                result.setDescription("用户令牌无效");
                inv.getController().renderJson(result);
            } else {
                inv.invoke();
            }
        }
    }
}
