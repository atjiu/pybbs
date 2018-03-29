package co.yiiu.config;

import co.yiiu.core.base.BaseController;
import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.model.Permission;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya at 2018/3/20
 */
@Component
public class SecurityConfig extends BaseController {

  public boolean hasRole(String role) {
    AdminUser adminUser = getAdminUser();
    return adminUser != null && adminUser.getRole().getName().equalsIgnoreCase(role);
  }

  public boolean hasPermission(String permission) {
    AdminUser adminUser = getAdminUser();
    if (adminUser == null || adminUser.getPermissions() == null) return false;
    for (Permission permission1 : adminUser.getPermissions()) {
      if (permission1.getValue().equalsIgnoreCase(permission)) {
        return true;
      }
    }
    return false;
  }
}
