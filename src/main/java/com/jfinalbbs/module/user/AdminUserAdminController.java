package com.jfinalbbs.module.user;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.utils.PasswordHelper;
import com.jfinalbbs.utils.StrUtil;
import com.jfinalbbs.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import java.util.Date;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/admin/adminuser", viewPath = "page/admin/sysconfig/adminuser")
public class AdminUserAdminController extends BaseController {

    @RequiresPermissions("setting:adminuser")
    public void index() {
        Page<AdminUser> page = AdminUser.me.page(getParaToInt("p", 1), defaultPageSize());
        setAttr("page", page);
        render("index.ftl");
    }

    @RequiresPermissions("setting:adminuser")
    @Before(Tx.class)
    public void add() {
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.GET)) {
            setAttr("roles", Role.me.findAll());
            render("add.ftl");
        } else {
            AdminUser adminUser = new AdminUser();
            adminUser.set("username", getPara("username"))
                    .set("password", getPara("password"))
                    .set("in_time", new Date());
            PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(adminUser);
            adminUser.save();
            //处理关联角色
            Integer[] roles = getParaValuesToInt("roles");
            AdminUser.me.correlationRole(adminUser.getInt("id"), roles);
            redirect("/admin/adminuser");
        }
    }

    @RequiresPermissions("setting:adminuser")
    @Before(Tx.class)
    public void edit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.GET)) {
            setAttr("adminUser", AdminUser.me.findById(id));
            setAttr("roles", Role.me.findAll());
            setAttr("uroles", UserRole.me.findByUserId(id));
            render("edit.ftl");
        } else {
            String password = getPara("password");
            String username = getPara("username");
            AdminUser adminUser = new AdminUser();
            adminUser.set("id", id)
                    .set("username", username)
                    .set("in_time", new Date());
            if(StrUtil.notBlank(password)) {
                adminUser.set("password", password);
                PasswordHelper passwordHelper = new PasswordHelper();
                passwordHelper.encryptPassword(adminUser);
            }
            adminUser.update();
            //处理关联角色
            Integer[] roles = getParaValuesToInt("roles");
            AdminUser.me.correlationRole(id, roles);
            //清理缓存保证角色设置及时生效
            clearCache(Constants.SHIROCACHE, Constants.ROLECACHEKEY + username);
            redirect("/admin/adminuser");
        }
    }

    @RequiresPermissions("setting:adminuser")
    @Before(Tx.class)
    public void delete() {
        Integer id = getParaToInt("id");
        if(id == null) {
            renderError(500);
        } else {
            AdminUser.me.deleteById(id);
            //删除关联的角色
            UserRole.me.deleteByUserId(id);
            success();
        }
    }

    @RequiresPermissions("setting:modifypwd")
    public void modifypwd() {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        String method = getRequest().getMethod();
        if(method.equalsIgnoreCase(Constants.GET)) {
            AdminUser adminUser = AdminUser.me.findByUsername(username);
            setAttr("adminUser", adminUser);
            render("modifypwd.ftl");
        } else if(method.equalsIgnoreCase(Constants.POST)) {
            String password = getPara("password");
            if(StrUtil.isBlank(password)) {
                setAttr("errMsg", "密码不能为空");
                render("modifypwd.ftl");
            } else {
                AdminUser adminUser = new AdminUser();
                adminUser.set("id", getParaToInt("id"))
                        .set("username", username)
                        .set("password", password);
                new PasswordHelper().encryptPassword(adminUser);
                adminUser.update();
                subject.logout();
                redirect("/adminlogin");
            }
        }
    }

}
