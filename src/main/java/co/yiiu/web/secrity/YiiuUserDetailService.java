package co.yiiu.web.secrity;

import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.service.PermissionService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YiiuUserDetailService implements UserDetailsService {

  private Logger log = Logger.getLogger(YiiuUserDetailService.class);

  @Autowired
  private UserService userService;
  @Autowired
  private PermissionService permissionService;

  public UserDetails loadUserByUsername(String username) {
    User user = userService.findByUsername(username);
    if (user != null) {
      List<Permission> permissions = permissionService.findByAdminUserId(user.getId());
      List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      for (Permission permission : permissions) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
        grantedAuthorities.add(grantedAuthority);
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
          true, true, true, !user.isBlock(), grantedAuthorities);
    } else {
      log.info("用户" + username + " 不存在");
      throw new UsernameNotFoundException("用户名或密码不正确");
    }
  }

}
