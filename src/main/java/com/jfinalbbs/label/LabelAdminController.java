package com.jfinalbbs.label;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@Before(AdminUserInterceptor.class)
public class LabelAdminController extends BaseController {

    public void index() {
        String name = getPara("name");
        setAttr("page", Label.me.page(getParaToInt("p", 1), PropKit.use("config.properties").getInt("page_size"), name));
        setAttr("name", name);
        render("index.html");
    }

    public void delete() {
        Integer id = getParaToInt("id");
        if(id != null) {
            if(Label.me.deleteById(id)) {
                //删除中间表里关联的话题
                LabelTopicId.me.deleteByLid(id);
                success();
            } else {
                error("删除失败");
            }
        } else {
            error(Constants.OP_ERROR_MESSAGE);
        }
    }

    public void add() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.RequestMethod.GET)) {
            render("add.html");
        } else if(method.equalsIgnoreCase(Constants.RequestMethod.POST)) {
            String name = getPara("name");
            getModel(Label.class).set("name", name).set("in_time", new Date()).set("topic_count", 0).save();
            redirect(Constants.getBaseUrl() + "/admin/label/index");
        }
    }
}
