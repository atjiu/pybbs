package co.yiiu.web.interceptor;

import co.yiiu.core.base.BaseEntity;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya at 2018/3/15
 */
@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

  @Autowired
  private BaseEntity baseEntity;

  /**
   * 如果session里没有用户信息，那么取cookie里的token，然后去数据库里查用户信息，再将用户信息存在session里
   * @param request
   * @param response
   * @param o
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
    AdminUser adminUser = baseEntity.getAdminUser();
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
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) {
    if(modelAndView != null) {
      AdminUser adminUser = baseEntity.getAdminUser();
      if (adminUser != null) modelAndView.addObject("admin_user", adminUser);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

  }
}
