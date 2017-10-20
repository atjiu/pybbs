package co.yiiu.web.tag;

import co.yiiu.core.base.BaseEntity;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class SpringSecurityTag {

  @Autowired
  private UserService userService;
  @Autowired
  private BaseEntity baseEntity;

  /**
   * Determine whether or not a topic is editable
   *
   * @param topic
   * @return
   */
  public boolean topicEditable(Topic topic) {
    if (topic == null) return false;

    //有权限可以直接编辑
    if (allGranted("topic:edit")) return true;

    //自己的话题且发布时间没有超过5分钟的可以编辑
    if (!baseEntity.overFiveMinute(topic.getInTime())
        && topic.getUser().getUsername().equals(getPrincipal()))
      return true;

    return false;
  }

  /**
   * Determine whether the user is admin
   *
   * @return
   */
  public boolean isAdmin() {
    String username = getPrincipal();
    if (username == null) {
      return false;
    } else {
      User user = userService.findByUsername(username);
      Set<Role> roles = user.getRoles();
      if (roles != null && roles.size() > 0) {
        for (Role role : roles) {
          if (role.getName().equals("admin")) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Get user lock statuc
   *
   * @return
   */
  public boolean isLock() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (obj instanceof org.springframework.security.core.userdetails.User) {
      return !((UserDetails) obj).isAccountNonLocked();
    } else {
      return true;
    }
  }

  /**
   * Get authenticated status
   *
   * @return
   */
  public boolean isAuthenticated() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (obj instanceof org.springframework.security.core.userdetails.User) {
      return true;
    }
    return false;
  }

  /**
   * Gets the user name of the user from the Authentication object
   *
   * @return the user name as string
   */
  public String getPrincipal() {
    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (obj instanceof org.springframework.security.core.userdetails.User) {
      return ((UserDetails) obj).getUsername();
    } else {
      return null;
    }
  }

  /**
   * Is the user granted all of the grantedAuthorities passed in
   *
   * @param checkForAuths a string array of grantedAuth
   * @return true if user has all of the listed authorities/roles, otherwise
   * false
   */
  public boolean allGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        continue;
      return false;
    }
    return true;
  }

  public boolean allGranted(String checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    if (userAuths.contains(checkForAuths))
      return true;
    return false;
  }

  /**
   * Is the user granted any of the grantedAuthorities passed into
   *
   * @param checkForAuths a string array of grantedAuth
   * @return true if user has any of the listed authorities/roles, otherwise
   * false
   */
  public boolean anyGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        return true;
    }
    return false;
  }

  /**
   * is the user granted none of the supplied roles
   *
   * @param checkForAuths a string array of roles
   * @return true only if none of listed roles are granted
   */
  public boolean noneGranted(String[] checkForAuths) {
    Set<String> userAuths = getUserAuthorities();
    for (String auth : checkForAuths) {
      if (userAuths.contains(auth))
        return false;
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  private Set<String> getUserAuthorities() {
    try {
      Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Set<String> roles = new HashSet<>();
      if (obj instanceof org.springframework.security.core.userdetails.User) {
        Collection<GrantedAuthority> gas = (Collection<GrantedAuthority>) ((UserDetails) obj).getAuthorities();
        for (GrantedAuthority ga : gas) {
          roles.add(ga.getAuthority());
        }
      }
      return roles;
    } catch (Exception e) {
      return new HashSet<>();
    }
  }

}
