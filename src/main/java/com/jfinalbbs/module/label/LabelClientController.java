package com.jfinalbbs.module.label;

import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.module.topic.Topic;
import com.jfinalbbs.utils.StrUtil;
import com.jfinalbbs.utils.ext.route.ControllerBind;

import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/api/label")
public class LabelClientController extends BaseController {

    public void index() {
        String name = getPara("name");
        if(StrUtil.isBlank(name)) {
            List<Label> labels = Label.me.findAll();
            setAttr("labels", labels);
            success(labels);
        } else {
            Label label = Label.me.findByName(name);
            Page<Topic> page = null;
            if(label != null) {
                page = Topic.me.paginate(getParaToInt("p", 1),
                        getParaToInt("size", defaultPageSize()), null, null, 1, label.getInt("id"));
            }
            success(page);
        }
    }

}
