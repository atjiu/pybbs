package com.jfinalbbs.module.label;

import com.jfinal.upload.UploadFile;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/label", viewPath = "page/admin/label")
public class LabelAdminController extends BaseController {

    @RequiresPermissions("menu:label")
    public void index() {
        String name = getPara("name");
        setAttr("page", Label.me.page(getParaToInt("p", 1), defaultPageSize(), name));
        setAttr("name", name);
        render("index.ftl");
    }

    @RequiresPermissions("label:delete")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id != null) {
            if (Label.me.deleteById(id)) {
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

    @RequiresPermissions("label:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(Constants.POST)) {
            UploadFile uploadFile = getFile("img", Constants.UPLOAD_DIR_LABEL);
            StringBuffer labelImg = new StringBuffer();
            if (uploadFile != null) {
                labelImg.append("/")
                        .append(Constants.UPLOAD_DIR)
                        .append("/")
                        .append(Constants.UPLOAD_DIR_LABEL)
                        .append("/")
                        .append(uploadFile.getFileName());
            }
            Label label = new Label();
            label.set("name", getPara("name"))
                    .set("description", getPara("description"))
                    .set("in_time", new Date())
                    .set("topic_count", 0)
                    .set("img", labelImg.toString())
                    .save();
            redirect("/admin/label/index");
        }
    }

    @RequiresPermissions("label:edit")
    public void edit() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(Constants.GET)) {
            Label label = Label.me.findById(getParaToInt(0));
            setAttr("label", label);
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(Constants.POST)) {
            UploadFile uploadFile = getFile("img", Constants.UPLOAD_DIR_LABEL);
            StringBuffer labelImg = new StringBuffer();
            if (uploadFile != null) {
                labelImg.append("/")
                        .append(Constants.UPLOAD_DIR)
                        .append("/")
                        .append(Constants.UPLOAD_DIR_LABEL)
                        .append("/")
                        .append(uploadFile.getFileName());
            }
            Label label = Label.me.findById(getParaToInt("id"));
            label.set("name", getPara("name"))
                    .set("description", getPara("description"))
                    .set("img", labelImg.toString())
                    .update();
            redirect("/admin/label/index");
        }
    }

}
