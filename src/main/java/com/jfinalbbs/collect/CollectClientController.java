package com.jfinalbbs.collect;

import com.jfinal.aop.Before;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.ClientInterceptor;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.user.User;
import com.jfinalbbs.utils.StrUtil;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(ClientInterceptor.class)
public class CollectClientController extends BaseController {

    public void index() {
        String tid = getPara("tid");
        if(StrUtil.isBlank(tid)) {
            error("话题id不能为空");
        } else if(Topic.me.findById(tid) == null) {
            error("无效话题");
        } else {
            String token = getPara("token");
            //根据token获取用户信息
            User user = getUser(token);
            Collect collect = Collect.me.findByTidAndAuthorId(tid, user.getStr("id"));
            if (collect != null) {
                error("已经收藏过，无需再次收藏");
            } else {
                collect = new Collect();
                boolean b = collect.set("tid", tid)
                        .set("author_id", user.get("id"))
                        .set("in_time", new Date()).save();
                if (!b) {
                    error("收藏失败");
                }
                success();
            }
        }
    }

    public void delete() {
        String tid = getPara("tid");
        if(StrUtil.isBlank(tid)) {
            error("话题id不能为空");
        } else if(Topic.me.findById(tid) == null) {
            error("无效话题");
        } else {
            String token = getPara("token");
            User user = getUser(token);
            Collect collect = Collect.me.findByTidAndAuthorId(tid, user.getStr("id"));
            if (collect == null) {
                renderText(Constants.OP_ERROR_MESSAGE);
            } else {
                boolean b = Collect.me.deleteByTidAndAuthorId(tid, user.getStr("id"));
                if (!b) {
                    error("取消收藏失败");
                }
                success();
            }
        }
    }
}
