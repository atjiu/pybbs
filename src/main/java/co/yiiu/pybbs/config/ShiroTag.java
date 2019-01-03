package co.yiiu.pybbs.config;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class ShiroTag {

  // 判断当前用户是否已经登录认证过
  public boolean isAuthenticated(){
    return SecurityUtils.getSubject().isAuthenticated();
  }

  // 获取当前用户的用户名
  public String getPrincipal() {
    return (String) SecurityUtils.getSubject().getPrincipal();
  }

  // 判断用户是否有 xx 角色
  public boolean hasRole(String name) {
    return SecurityUtils.getSubject().hasRole(name);
  }

  // 判断用户是否有 xx 权限
  public boolean hasPermission(String name) {
    return !StringUtils.isEmpty(name) && SecurityUtils.getSubject().isPermitted(name);
  }

  // 判断用户是否有 xx 权限
  public boolean hasPermissionOr(String... name) {
    boolean[] permitted = SecurityUtils.getSubject().isPermitted(name);
    for (boolean b : permitted) {
      // 如果有一个权限，就成功
      if (b) {
        return true;
      }
    }
    return false;
  }

  // 判断用户是否有 xx 权限
  public boolean hasPermissionAnd(String... name) {
    boolean[] permitted = SecurityUtils.getSubject().isPermitted(name);
    for (boolean b : permitted) {
      // 必须所有的权限都有，才成功
      if (!b) {
        return false;
      }
    }
    return true;
  }

  // 判断用户是否有 xx 权限
  public boolean hasAllPermission(String... name) {
    return SecurityUtils.getSubject().isPermittedAll(name);
  }
}
