package co.yiiu.module.security.core;

import co.yiiu.module.security.model.AdminUser;
import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.service.AdminUserService;
import co.yiiu.module.security.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private PermissionService permissionService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AdminUser adminUser = adminUserService.findByUsername(username);
    List<Permission> permissions = permissionService.findByUserId(adminUser.getId());
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (Permission permission : permissions) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getValue());
      grantedAuthorities.add(grantedAuthority);
    }
    return new User(adminUser.getUsername(), adminUser.getPassword(), grantedAuthorities);
  }
}
