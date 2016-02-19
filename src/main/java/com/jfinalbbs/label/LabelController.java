package com.jfinalbbs.label;

import com.jfinal.plugin.activerecord.Page;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.utils.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class LabelController extends BaseController {

    public void index() throws UnsupportedEncodingException {
        String name = getPara(0);
        if(StrUtil.isBlank(name)) {
            List<Label> labels = Label.me.findAll();
            setAttr("labels", labels);
            render("front/label/list.ftl");
        } else {
            name = URLDecoder.decode(name, "UTF-8");
            Label label = Label.me.findByName(name);
            setAttr("label", label);
            if(label != null) {
                Page<Topic> page = Topic.me.paginate(getParaToInt("p", 1),
                        getParaToInt("size", defaultPageSize()), null, null, 1, label.getInt("id"));
                setAttr("page", page);
            }
            render("front/label/index.ftl");
        }
    }

    public void search() {
        String q = getPara("q");
        if (StrUtil.isBlank(q)) {
            renderJson(new ArrayList<String>());
        } else {
            List<Label> labels = Label.me.findByNameLike(q);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < labels.size(); i++) {
                list.add(labels.get(i).getStr("name"));
            }
            renderJson(list);
        }
    }

}
