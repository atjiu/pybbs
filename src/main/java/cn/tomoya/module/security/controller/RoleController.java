package cn.tomoya.module.security.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.security.entity.Role;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * role list
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.role.list"));
        return render("/admin/role/list");
    }

    /**
     * add role
     *
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("list", permissionService.findAll());
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.role.add"));
        return render("/admin/role/add");
    }

    /**
     * save role and associated permissions
     *
     * @param permissionIds
     * @param response
     * @return
     */
    @PostMapping("/add")
    public String save(String name, String description, Integer[] permissionIds, HttpServletResponse response) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        Set<Permission> permissions = new HashSet<>();
        for (int i : permissionIds) {
            Permission permission = permissionService.findById(i);
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        roleService.save(role);
        userService.clearCache();
        roleService.clearCache();
        return redirect(response, "/admin/role/list");
    }

    /**
     * edit role page
     *
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("list", permissionService.findAll());
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.role.edit"));
        return render("/admin/role/edit");
    }

    /**
     * update role and associated permissions
     *
     * @param permissionIds
     * @param response
     * @return
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, String name, String description, Integer[] permissionIds, HttpServletResponse response) {
        Role role = roleService.findById(id);
        role.setName(name);
        role.setDescription(description);
        Set<Permission> permissions = new HashSet<>();
        if(permissionIds != null) {
            for (int i : permissionIds) {
                Permission permission = permissionService.findById(i);
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        roleService.save(role);
        userService.clearCache();
        roleService.clearCache();
        return redirect(response, "/admin/role/list");
    }

    /**
     * delete role
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, HttpServletResponse response) {
        roleService.deleteById(id);
        userService.clearCache();
        roleService.clearCache();
        return redirect(response, "/admin/role/list");
    }
}
