package com.jfinalbbs.collect;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.UserInterceptor;
import com.jfinalbbs.user.User;
import com.jfinal.aop.Before;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class CollectController extends BaseController {

    @Before(UserInterceptor.class)
    public void index() {
        String tid = getPara(0);
        User user = getSessionAttr(Constants.USER_SESSION);
        Collect collect = Collect.me.findByTidAndAuthorId(tid, user.getStr("id"));
        if(collect != null) {
            error("已经收藏过，无需再次收藏");
        } else {
            collect = new Collect();
            boolean b = collect.set("tid", tid)
                    .set("author_id", user.get("id"))
                    .set("in_time", new Date()).save();
            if(!b) {
                error("收藏失败");
            }
            success();
        }
    }

    @Before(UserInterceptor.class)
    public void delete() {
        String tid = getPara(0);
        User user = getSessionAttr(Constants.USER_SESSION);
        Collect collect = Collect.me.findByTidAndAuthorId(tid, user.getStr("id"));
        if(collect == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            boolean b = Collect.me.deleteByTidAndAuthorId(tid, user.getStr("id"));
            if(!b) {
                error("取消收藏失败");
            }
            success();
        }
    }
}
