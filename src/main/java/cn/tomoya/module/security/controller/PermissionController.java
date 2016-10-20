package cn.tomoya.module.security.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.security.entity.Role;
import cn.tomoya.module.security.service.PermissionService;
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
import java.util.List;

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

    /**
     * 角色列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Integer pid, Model model) {
        if(pid == null || pid == 0) {
            model.addAttribute("childPermissions", permissionService.findAllChildPermission());
            model.addAttribute("permissions", permissionService.findByPid(0));
        } else {
            model.addAttribute("childPermissions", permissionService.findByPid(pid));
            model.addAttribute("permissions", permissionService.findByPid(0));
        }
        model.addAttribute("pid", pid);
        return render("/admin/permission/list");
    }

    /**
     * 跳转添加页面
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Integer pid, Model model) {
        model.addAttribute("pid", pid);
        model.addAttribute("permissions", permissionService.findByPid(0));
        return render("/admin/permission/add");
    }

    /**
     * 保存添加的权限
     * @param pid
     * @param name
     * @param description
     * @param url
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(Integer pid, String name, String description, String url) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        permission.setUrl(url);
        permission.setPid(pid == null ? 0 : pid);
        permissionService.save(permission);
        return redirect("/admin/permission/list");
    }

    /**
     * 跳转编辑页面
     * @Param id
     * @Param model
     * @return
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("permission", permissionService.findById(id));
        model.addAttribute("permissions", permissionService.findByPid(0));
        return render("/admin/permission/edit");
    }

    /**
     * 更新权限
     * @param id
     * @param pid
     * @param name
     * @param description
     * @param url
     * @return
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String update(@PathVariable Integer id, Integer pid, String name, String description, String url) {
        Permission permission = permissionService.findById(id);
        permission.setName(name);
        permission.setDescription(description);
        permission.setUrl(url);
        permission.setPid(pid == null ? 0 : pid);
        permissionService.save(permission);
        return redirect("/admin/permission/list");
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable Integer id, HttpServletResponse response) {
        permissionService.deleteById(id);
        return redirect(response, "/admin/permission/list");
    }
}
