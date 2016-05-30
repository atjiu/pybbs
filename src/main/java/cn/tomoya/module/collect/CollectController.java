package cn.tomoya.module.collect;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.module.user.User;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/collect", viewPath = "WEB-INF/page")
public class CollectController extends BaseController {

    /**
     * 收藏话题
     */
    @Before(UserInterceptor.class)
    public void add() {
        Integer tid = getParaToInt("tid");
        User user = getUser();
        Collect collect = new Collect();
        collect.set("tid", tid)
                .set("uid", user.getInt("id"))
                .set("in_time", new Date())
                .save();
        clearCache(Constants.COLLECT_CACHE, Constants.COLLECT_CACHE_KEY + tid);
        clearCache(Constants.COLLECT_CACHE, Constants.COLLECT_CACHE_KEY + tid + "_" + user.getInt("id"));
        redirect("/t/" + tid);
    }

    /**
     * 取消收藏话题
     */
    @Before(UserInterceptor.class)
    public void delete() {
        Integer tid = getParaToInt("tid");
        User user = getUser();
        Collect collect = Collect.me.findByTidAndUid(tid, user.getInt("id"));
        if(collect == null) {
            renderText("请先收藏");
        } else {
            collect.delete();
            clearCache(Constants.COLLECT_CACHE, Constants.COLLECT_CACHE_KEY + tid);
            clearCache(Constants.COLLECT_CACHE, Constants.COLLECT_CACHE_KEY + tid + "_" + user.getInt("id"));
            redirect("/t/" + tid);
        }
    }
}
