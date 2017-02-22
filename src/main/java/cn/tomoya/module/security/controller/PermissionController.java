package cn.tomoya.module.security.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.security.service.PermissionService;
import cn.tomoya.module.security.service.RoleService;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * permissions page
     *
     * @param pid
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer pid, Model model) {
        if (pid == null || pid == 0) {
            model.addAttribute("childPermissions", permissionService.findAllChildPermission());
            model.addAttribute("permissions", permissionService.findByPid(0));
        } else {
            model.addAttribute("childPermissions", permissionService.findByPid(pid));
            model.addAttribute("permissions", permissionService.findByPid(0));
        }
        model.addAttribute("pid", pid);
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.permission.list"));
        return render("/admin/permission/list");
    }

    /**
     * add permission page
     *
     * @return
     */
    @GetMapping("/add")
    public String add(Integer pid, Model model) {
        model.addAttribute("pid", pid);
        model.addAttribute("permissions", permissionService.findByPid(0));
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.permission.add"));
        return render("/admin/permission/add");
    }

    /**
     * save permission
     *
     * @param pid
     * @param name
     * @param description
     * @param url
     * @return
     */
    @PostMapping("/add")
    public String save(Integer pid, String name, String description, String url, HttpServletResponse response) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        permission.setUrl(url);
        permission.setPid(pid == null ? 0 : pid);
        permissionService.save(permission);
        roleService.clearCache();
        userService.clearCache();
        permissionService.clearCache();
        return redirect(response, "/admin/permission/list?pid=" + pid);
    }

    /**
     * edit permission page
     *
     * @return
     * @Param id
     * @Param model
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("permission", permissionService.findById(id));
        model.addAttribute("permissions", permissionService.findByPid(0));
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.permission.edit"));
        return render("/admin/permission/edit");
    }

    /**
     * update permission
     *
     * @param id
     * @param pid
     * @param name
     * @param description
     * @param url
     * @return
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, Integer pid, String name, String description, String url, HttpServletResponse response) {
        Permission permission = permissionService.findById(id);
        permission.setName(name);
        permission.setDescription(description);
        permission.setUrl(url);
        permission.setPid(pid == null ? 0 : pid);
        permissionService.save(permission);
        roleService.clearCache();
        userService.clearCache();
        permissionService.clearCache();
        return redirect(response, "/admin/permission/list?pid=" + pid);
    }

    /**
     * delete permission
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, HttpServletResponse response) {
        permissionService.deleteById(id);
        roleService.clearCache();
        userService.clearCache();
        permissionService.clearCache();
        return redirect(response, "/admin/permission/list");
    }
}
