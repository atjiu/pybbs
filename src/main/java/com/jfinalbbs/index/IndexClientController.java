package com.jfinalbbs.index;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.topic.Topic;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class IndexClientController extends BaseController {

    public void index() {
        String tab = getPara("tab");
        String q = getPara("q");
        if(tab == null) tab = "all";
        Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1),
                getParaToInt("size", PropKit.use("config.properties").getInt("page_size")), tab, q, 1, null);
        success(page);
    }
}