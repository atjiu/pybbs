package cn.tomoya.module.section;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.Constants;
import cn.tomoya.interceptor.PermissionInterceptor;
import cn.tomoya.interceptor.UserInterceptor;
import cn.tomoya.utils.ext.route.ControllerBind;
import com.jfinal.aop.Before;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Before({
        UserInterceptor.class,
        PermissionInterceptor.class
})
@ControllerBind(controllerKey = "/section", viewPath = "WEB-INF/page")
public class SectionController extends BaseController {

    /**
     * 板块列表
     */
    public void list() {
        setAttr("sections", Section.me.findAll());
        render("section/list.ftl");
    }

    /**
     * 改变板块显示状态
     */
    public void changeshowstatus() {
        Integer id = getParaToInt("id");
        Section section = Section.me.findById(id);
        section.set("show_status", !section.getBoolean("show_status")).update();
        clearCache(Constants.CacheEnum.sections.name() + true);
        clearCache(Constants.CacheEnum.sections.name() + false);
        clearCache(Constants.CacheEnum.section.name() + section.getStr("tab"));
        redirect("/section/list");
    }

    /**
     * 删除板块
     */
    public void delete() {
        Integer id = getParaToInt("id");
        Section section = Section.me.findById(id);
        section.delete();
        clearCache(Constants.CacheEnum.sections.name() + true);
        clearCache(Constants.CacheEnum.sections.name() + false);
        clearCache(Constants.CacheEnum.section.name() + section.getStr("tab"));
        redirect("/section/list");
    }

    /**
     * 添加板块
     */
    public void add() {
        String method = getRequest().getMethod();
        if(method.equals("GET")) {
            render("section/add.ftl");
        } else if(method.equals("POST")) {
            String name = getPara("name");
            String tab = getPara("tab");
            Integer showStatus = getParaToInt("showStatus");
            Section section = new Section();
            section.set("name", name)
                    .set("tab", tab)
                    .set("show_status", showStatus == 1)
                    .set("display_index", 99)
                    .set("default_show", 0)
                    .save();
            redirect("/section/list");
        }
    }

    /**
     * 编辑板块
     */
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        Section section = Section.me.findById(id);
        if(method.equals("GET")) {
            setAttr("section", section);
            render("section/edit.ftl");
        } else if(method.equals("POST")) {
            String name = getPara("name");
            String tab = getPara("tab");
            Integer showStatus = getParaToInt("showStatus");
            section.set("id", id)
                    .set("name", name)
                    .set("tab", tab)
                    .set("show_status", showStatus == 1)
                    .update();
            redirect("/section/list");
        }
    }
}
