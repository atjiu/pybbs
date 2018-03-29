package co.yiiu.web.interceptor;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.util.CookieHelper;
import co.yiiu.core.util.security.Base64Helper;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.AdminUserService;
import co.yiiu.module.security.service.PermissionService;
import co.yiiu.module.security.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by tomoya at 2018/3/15
 */
@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private RoleService roleService;
  @Autowired
  private PermissionService permissionService;

  /**
   * 如果session里没有用户信息，那么取cookie里的token，然后去数据库里查用户信息，再将用户信息存在session里
   * @param request
   * @param response
   * @param o
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
    HttpSession session = request.getSession();
    AdminUser adminUser = (AdminUser) session.getAttribute("admin_user");
    if(adminUser == null) {
      String token = CookieHelper.getValue(request, siteConfig.getCookie().getAdminUserName());
      if(!StringUtils.isEmpty(token)) {
        try {
          token = new String(Base64Helper.decode(token));
        } catch (Exception e) {
          log.error(e.getLocalizedMessage());
          CookieHelper.clearCookieByName(response, siteConfig.getCookie().getAdminUserName());
        }
        adminUser = adminUserService.findByToken(token);
        if(adminUser == null) {
          CookieHelper.clearCookieByName(response, siteConfig.getCookie().getAdminUserName());
        } else {
          // 查询用户的角色权限封装进adminuser里
          Role role = roleService.findById(adminUser.getRoleId());
          List<Permission> permissions = permissionService.findByUserId(adminUser.getId());
          adminUser.setRole(role);
          adminUser.setPermissions(permissions);
          session.setAttribute("admin_user", adminUser);
        }
      }
    }
    Assert.notNull(adminUser, "请先登录，点击去<a href='/admin/login'>登录</a>");
    // 鉴权
    boolean flag = false;
    String requestURI = request.getRequestURI();
    for (Permission permission: adminUser.getPermissions()) {
      if(permission.getUrl().equals(requestURI)) {
        flag = true;
        break;
      }
    }
    Assert.isTrue(flag, "你没有权限访问此资源");
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

  }
}
