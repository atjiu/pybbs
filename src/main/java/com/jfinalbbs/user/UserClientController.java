package com.jfinalbbs.user;

import com.jfinal.aop.Before;
import com.jfinalbbs.collect.Collect;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.interceptor.ClientInterceptor;
import com.jfinalbbs.topic.Topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(ClientInterceptor.class)
public class UserClientController extends BaseController {

    public void index() {
        Map<String, Object> map = new HashMap<String, Object>();
        String token = getPara("token");
        User user = getUser(token);
        map.put("user", user);
        List<Topic> topics = Topic.me.paginateByAuthorId(1, 10, user.getStr("id")).getList();
        map.put("topics", topics);
        List<Collect> collects = Collect.me.findByAuthorIdWithTopic(user.getStr("id"));
        map.put("collects", collects);
        success(map);
    }
}
