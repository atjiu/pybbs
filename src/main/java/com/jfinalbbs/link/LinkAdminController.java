package com.jfinalbbs.link;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class LinkAdminController extends BaseController {

    public void index() {
        setAttr("admin_links", Link.me.findAll());
        render("index.ftl");
    }

    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(Constants.POST)) {
            Integer maxDisplayIndex = Link.me.maxDisplayIndex();
            if (maxDisplayIndex == null) maxDisplayIndex = 0;
            getModel(Link.class).set("display_index", maxDisplayIndex + 1).save();
            clearCache(Constants.LINKCACHE, Constants.LINKLISTKEY);
            redirect(baseUrl() + "/admin/link");
        }
    }

    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if (method.equalsIgnoreCase(Constants.GET)) {
            setAttr("link", Link.me.findById(id));
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(Constants.POST)) {
            getModel(Link.class).update();
            clearCache(Constants.LINKCACHE, Constants.LINKLISTKEY);
            redirect(baseUrl() + "/admin/link");
        }
    }

    public void delete() {
        Integer id = getParaToInt("id");
        if (id == null) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            try {
                Link.me.deleteById(id);
                clearCache(Constants.LINKCACHE, Constants.LINKLISTKEY);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.DELETE_FAILURE);
            }
        }
    }

    public void sort() {
        Integer[] ids = getParaValuesToInt("ids");
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Link.me.findById(ids[i]).set("display_index", i + 1).update();
            }
            clearCache(Constants.LINKCACHE, Constants.LINKLISTKEY);
        }
        redirect(baseUrl() + "/admin/link");
    }
}
