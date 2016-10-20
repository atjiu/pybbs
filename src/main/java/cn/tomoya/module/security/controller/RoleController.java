package cn.tomoya.module.security.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.security.entity.Role;
import cn.tomoya.module.security.service.PermissionService;
import cn.tomoya.module.security.service.RoleService;
import cn.tomoya.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    private PermissionService permissionService;

    /**
     * 角色列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return render("/admin/role/list");
    }

    /**
     * 添加角色
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("list", permissionService.findAll());
        return render("/admin/role/add");
    }

    /**
     * 保存配置的权限
     * @param permissionIds
     * @param response
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(String name, String description, Integer[] permissionIds, HttpServletResponse response) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        Set<Permission> permissions = new HashSet<>();
        for(int i : permissionIds) {
            Permission permission = permissionService.findById(i);
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        roleService.save(role);
        return redirect(response, "/admin/role/list");
    }

    /**
     * 编辑角色
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("list", permissionService.findAll());
        return render("/admin/role/edit");
    }

    /**
     * 更新配置的权限
     * @param permissionIds
     * @param response
     * @return
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String update(@PathVariable Integer id, String name, String description, Integer[] permissionIds, HttpServletResponse response) {
        Role role = roleService.findById(id);
        role.setName(name);
        role.setDescription(description);
        Set<Permission> permissions = new HashSet<>();
        for(int i : permissionIds) {
            Permission permission = permissionService.findById(i);
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        roleService.save(role);
        return redirect(response, "/admin/role/list");
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable Integer id, HttpServletResponse response) {
        roleService.deleteById(id);
        return redirect(response, "/admin/role/list");
    }
}
