package cn.tomoya.module.security.core;

import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.security.service.PermissionService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
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
public class MyUserDetailService implements UserDetailsService {

    Logger log = Logger.getLogger(MyUserDetailService.class);

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
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            log.info("user " + username + " does not exist");
            throw new UsernameNotFoundException("username or password is incorrect");
        }
    }

}
