package com.jfinalbbs.label;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class LabelController extends BaseController {

    public void search() {
        String q = getPara("q");
        if(StrUtil.isBlank(q)) {
            renderJson(new ArrayList<String>());
        } else {
            List<Label> labels = Label.me.findByNameLike(q);
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < labels.size(); i++) {
                list.add(labels.get(i).getStr("name"));
            }
            renderJson(list);
        }
    }
}
