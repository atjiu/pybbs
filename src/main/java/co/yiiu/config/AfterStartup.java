package co.yiiu.config;

import co.yiiu.config.data.DataConfig;
import co.yiiu.config.data.DataUser;
import co.yiiu.module.security.model.Permission;
import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.service.PermissionService;
import co.yiiu.module.security.service.RoleService;
import co.yiiu.module.system.model.System;
import co.yiiu.module.system.service.SystemService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@Service
@Transactional
public class AfterStartup implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private SystemService systemService;
  @Autowired
  private PermissionService permissionService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserService userService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private DataConfig dataConfig;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    System system = systemService.findByName("init");
    if(system == null) {
      system = new System();
      system.setName("init");
      system.setValue("1");
      systemService.save(system);

      insert();
    } else {
      if(system.getValue().equalsIgnoreCase("0")) {
        // 系统已经初始化过了，这里有人为修改过的
        // 添加你想要的处理
      }
    }
  }

  private void destory() {

  }

  public void insert() {
    // save permission
    dataConfig.get_permissions().forEach(dataPermission -> {
      Permission permission = new Permission();
      permission.setName(dataPermission.getName());
      permission.setDescription(dataPermission.getDescription());
      permission.setPid(0);
      permissionService.save(permission);
      //save child permission
      dataPermission.getChilds().forEach(childPermission -> {
        Permission permission1 = new Permission();
        permission1.setPid(permission.getId());
        permission1.setName(childPermission.getName());
        permission1.setDescription(childPermission.getDescription());
        permission1.setUrl(childPermission.getUrl());
        permissionService.save(permission1);
      });
    });

    //save role
    dataConfig.get_roles().forEach(dataRole -> {
      Role role = new Role();
      role.setName(dataRole.getName());
      role.setDescription(dataRole.getDescription());
      Set<Permission> permissions = new HashSet<>();
      // associate permission
      dataRole.getPermissions().forEach(permissionName -> {
        Permission permission = permissionService.findByName(permissionName);
        permissions.add(permission);
      });
      role.setPermissions(permissions);
      roleService.save(role);
    });

    //save user
    DataUser dataUser = dataConfig.get_user();
    Role role = roleService.findByName(dataUser.getRole());
    User user = new User();
    user.setUsername(dataUser.getUsername());
    user.setPassword(dataUser.getPassword());
    user.setEmail(dataUser.getEmail());
    user.setBio(dataUser.getBio());
    user.setUrl(dataUser.getUrl());
    user.setInTime(new Date());
    user.setBlock(false);
    user.setToken(UUID.randomUUID().toString());
    user.setAvatar(dataUser.getAvatar());
    user.setAttempts(0);
    user.setScore(siteConfig.getScore());
    user.setSpaceSize(siteConfig.getUserUploadSpaceSize());
    // associate role
    Set<Role> roles = new HashSet<>();
    roles.add(role);
    user.setRoles(roles);
    userService.save(user);
  }

}
