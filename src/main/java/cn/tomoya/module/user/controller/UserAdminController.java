package cn.tomoya.module.user.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.security.entity.Role;
import cn.tomoya.module.security.service.RoleService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.user.entity.User;
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
@RequestMapping("/admin/user")
public class UserAdminController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * user list
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        model.addAttribute("page", userService.pageUser(p == null ? 1 : p, settingService.getPageSize()));
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.user.list"));
        return render("/admin/user/list");
    }

    /**
     * delete user
     *
     * @param id
     * @return
     */
//    @GetMapping(value = "/{id}/delete")
//    public String delete(@PathVariable Integer id, HttpServletResponse response) {
//        userService.deleteById(id);
//        return redirect(response, "/admin/user/list");
//    }

    /**
     * disable the user
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}/block")
    public String block(@PathVariable Integer id, HttpServletResponse response) {
        userService.blockUser(id);
        userService.clearCache();
        return redirect(response, "/admin/user/list");
    }

    /**
     * unblock users
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}/unblock")
    public String unblock(@PathVariable Integer id, HttpServletResponse response) {
        userService.unBlockUser(id);
        userService.clearCache();
        return redirect(response, "/admin/user/list");
    }

    /**
     * edit user and associated roles
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/role")
    public String role(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.user.edit"));
        return render("/admin/user/role");
    }

    /**
     * save user and associated roles
     *
     * @param id
     * @return
     */
    @PostMapping("/{id}/role")
    public String saveRole(@PathVariable Integer id, Integer[] roleIds, HttpServletResponse response) {
        User user = userService.findById(id);
        Set<Role> roles = new HashSet<>();
        for (int i : roleIds) {
            Role role = roleService.findById(i);
            roles.add(role);
        }
        user.setRoles(roles);
        userService.save(user);
        userService.clearCache();
        return redirect(response, "/admin/user/list");
    }

}
