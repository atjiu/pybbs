package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.AdminUserService;
import co.yiiu.module.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/admin_user")
public class AdminUserController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private RoleService roleService;

  @GetMapping("/list")
  public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
    model.addAttribute("page", adminUserService.page(pageNo, siteConfig.getPageSize()));
    return "admin/admin_user/list";
  }

  @GetMapping("/add")
  public String add(Model model) {
    model.addAttribute("roles", roleService.findAll());
    return "admin/admin_user/add";
  }

  @PostMapping("/add")
  @ResponseBody
  public Result save(String username, String password, Integer roleId) {
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notEmpty(password, "密码不能为空");
    ApiAssert.notNull(roleId, "请选择角色");

    AdminUser adminUser = adminUserService.findByUsername(username);
    ApiAssert.isNull(adminUser, "用户名被占用了");

    adminUser = new AdminUser();
    adminUser.setUsername(username);
    adminUser.setPassword(new BCryptPasswordEncoder().encode(password));
    adminUser.setRoleId(roleId);
    adminUser.setToken(UUID.randomUUID().toString());
    adminUser.setInTime(new Date());
    adminUserService.save(adminUser);
    return Result.success();
  }

  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    model.addAttribute("adminUser", adminUserService.findOne(id));
    List<Role> roles = roleService.findAll();
    model.addAttribute("roles", roles);
    return "admin/admin_user/edit";
  }

  @PostMapping("/edit")
  @ResponseBody
  public Result update(Integer id, String username, String oldPassword, String password, Integer roleId) {
    ApiAssert.notNull(id, "用户ID不存在");
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notNull(roleId, "请选择角色");

    AdminUser adminUser = adminUserService.findOne(id);
    ApiAssert.notNull(adminUser, "用户不存在");
    adminUser.setUsername(username);
    if(!StringUtils.isEmpty(oldPassword) && !StringUtils.isEmpty(password)) {
      ApiAssert.isTrue(new BCryptPasswordEncoder().matches(oldPassword, adminUser.getPassword()), "旧密码不正确");
      adminUser.setPassword(new BCryptPasswordEncoder().encode(password));
    }
    adminUser.setRoleId(roleId);
    adminUserService.save(adminUser);
    return Result.success();
  }

  @GetMapping("/delete")
  public String delete(Integer id) {
    adminUserService.deleteById(id);
    return redirect("/admin/admin_user/list");
  }
}
