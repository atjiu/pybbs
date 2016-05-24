package com.jfinalbbs.module.section;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.utils.StrUtil;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/section", viewPath = "page/admin/section")
public class SectionAdminController extends BaseController {

    // 查询板块列表
    @RequiresPermissions("menu:section")
    public void index() {
        setAttr("admin_sections", Section.me.findAll());
        render("index.ftl");
    }

    // 添加板块
    @RequiresPermissions("section:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(Constants.POST)) {
            String name = getPara("name");
            Integer show_status = getParaToInt("show_status");
            String tab = getPara("tab");
            Section section = new Section();
            section.set("name", name).set("show_status", show_status).set("tab", tab).set("display_index", 99).save();
            // clear cache
            clearCache(Constants.SECTIONCACHE, null);
            redirect("/admin/section/index");
        }
    }

    // 编辑板块
    @RequiresPermissions("section:edit")
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if (method.equalsIgnoreCase(Constants.GET)) {
            setAttr("section", Section.me.findById(id));
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(Constants.POST)) {
            String name = getPara("name");
            Integer show_status = getParaToInt("show_status");
            String tab = getPara("tab");
            Section section = Section.me.findById(id);
            section.set("name", name).set("show_status", show_status).set("tab", tab).update();
            // clear cache
            clearCache(Constants.SECTIONCACHE, null);
            redirect("/admin/section/index");
        }
    }

    // 排序
    @RequiresPermissions("section:sort")
    public void sort() {
        Integer[] ids = getParaValuesToInt("ids");
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Section section = Section.me.findById(ids[i]);
                section.set("display_index", i + 1).update();
            }
        }
        // clear cache
        clearCache(Constants.SECTIONCACHE, null);
        redirect("/admin/section/index");
    }

    // 删除板块
    @RequiresPermissions("section:delete")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id == null) {
            error(Constants.OP_ERROR_MESSAGE);
        } else {
            try {
                Section.me.deleteById(id);
                clearCache(Constants.SECTIONCACHE, null);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(Constants.DELETE_FAILURE);
            }
        }
    }

    @RequiresPermissions("section:setDefault")
    public void setDefault() {
        String tab = getPara("tab");
        if (StrUtil.isBlank(tab)) {
            error("设置失败");
        } else {
            Section defaultSection = Section.me.findDefault();
            if (!tab.equals(defaultSection.getStr("tab"))) {
                defaultSection.set("default_show", 0).update();
                Section section = Section.me.findByTab(tab);
                section.set("default_show", 1).update();
                clearCache(Constants.SECTIONCACHE, null);
            }
            success();
        }
    }
}
