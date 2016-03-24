package com.jfinalbbs.module.index;

import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.module.topic.Topic;
import com.jfinalbbs.utils.ext.route.ControllerBind;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/api/index")
public class IndexClientController extends BaseController {

    public void index() {
        String tab = getPara("tab", "all");
        String q = getPara("q");
        Integer l = getParaToInt("l", 0);
        if (l != null && l > 0) {
            tab = "all";
        }
        Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1),
                getParaToInt("size", defaultPageSize()), tab, q, 1, l);
        for(Topic t : page.getList()) {
//            t.set("content", )
        }
        success(page);
    }
}