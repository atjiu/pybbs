package co.yiiu.web.admin;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.core.util.CookieHelper;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.core.util.security.crypto.BCryptPasswordEncoder;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.AdminUserService;
import co.yiiu.module.security.service.PermissionService;
import co.yiiu.module.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController extends BaseController {

  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private RoleService roleService;
  @Autowired
  private PermissionService permissionService;

  @GetMapping("/index")
  public String index() {
    return "admin/index";
  }

  @GetMapping("/login")
  public String login() {
    return "admin/login";
  }

  @PostMapping("/login")
  @ResponseBody
  public Result login(String username, String password, String code, Boolean rememberMe, HttpServletResponse response, HttpSession session) {
    ApiAssert.notEmpty(username, "用户名不能为空");
    ApiAssert.notEmpty(password, "密码不能为空");
    ApiAssert.notEmpty(code, "验证码不能为空");

    String index_code = (String) session.getAttribute("index_code");
    ApiAssert.isTrue(code.equalsIgnoreCase(index_code), "验证码不正确");

    AdminUser adminUser = adminUserService.findByUsername(username);
    ApiAssert.notNull(adminUser, "用户不存在");
    ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, adminUser.getPassword()), "密码不正确");

    // 查询用户的角色权限封装进adminuser里
    Role role = roleService.findById(adminUser.getRoleId());
    List<Permission> permissions = permissionService.findByUserId(adminUser.getId());
    adminUser.setRole(role);
    adminUser.setPermissions(permissions);

    session.setAttribute("admin_user", adminUser);
    if(rememberMe) {
      CookieHelper.addCookie(
          response,
          siteConfig.getCookie().getDomain(),
          "/admin/",
          siteConfig.getCookie().getAdminUserName(),
          Base64Helper.encode(adminUser.getToken().getBytes()),
          siteConfig.getCookie().getAdminUserMaxAge() * 24 * 60 * 60,
          true,
          false
      );
    }
    return Result.success();
  }

  @GetMapping("/logout")
  public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    session.removeAttribute("admin_user");
    CookieHelper.clearCookieByName(request, response, siteConfig.getCookie().getAdminUserName(),
        siteConfig.getCookie().getDomain(), "/admin/");
    return redirect("/admin/login");
  }
}
