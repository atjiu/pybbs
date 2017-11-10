package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.RoleService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
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
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;

  /**
   * 用户列表
   *
   * @param p
   * @param model
   * @return
   */
  @GetMapping("/list")
  public String list(Integer p, Model model) {
    model.addAttribute("page", userService.pageUser(p == null ? 1 : p, siteConfig.getPageSize()));
    return "admin/user/list";
  }

  /**
   * 禁用用户
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/block")
  public String block(@PathVariable Integer id, HttpServletResponse response) {
    userService.blockUser(id);
    return redirect(response, "/admin/user/list");
  }

  /**
   * 解禁用户
   *
   * @param id
   * @param response
   * @return
   */
  @GetMapping("/{id}/unblock")
  public String unblock(@PathVariable Integer id, HttpServletResponse response) {
    userService.unBlockUser(id);
    return redirect(response, "/admin/user/list");
  }

  /**
   * 配置用户的角色
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}/role")
  public String role(@PathVariable Integer id, Model model) {
    model.addAttribute("user", userService.findById(id));
    model.addAttribute("roles", roleService.findAll());
    return "admin/user/role";
  }

  /**
   * 保存配置用户的角色
   *
   * @param id
   * @return
   */
  @PostMapping("/{id}/role")
  public String saveRole(@PathVariable Integer id, int score, Integer roleId, HttpServletResponse response) {
    User user = userService.findById(id);
    Set<Role> roles = new HashSet<>();
    Role role = roleService.findById(roleId);
    roles.add(role);
    //TODO 记录日志
    user.setScore(score);
    user.setRoles(roles);
    userService.save(user);
    return redirect(response, "/admin/user/list");
  }

}
