package cn.tomoya.module.security.service;

import cn.tomoya.module.security.dao.PermissionDao;
import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private UserService userService;

    /**
     * query all permissions
     *
     * @return
     */
    public List<Permission> findAllChildPermission() {
        return permissionDao.findByPidGreaterThan(0);
    }

    /**
     * query permissions by parent node id
     *
     * @param pid
     * @return
     */
    public List<Permission> findByPid(int pid) {
        return permissionDao.findByPid(pid);
    }

    /**
     * query permission list include children permission list
     *
     * @return
     */
    public List findAll() {
        List list = new ArrayList();
        Map map;
        List<Permission> permissions = permissionDao.findByPid(0);
        for (Permission permission : permissions) {
            map = new HashMap();
            map.put("permission", permission);
            map.put("childPermissions", permissionDao.findByPid(permission.getId()));
            list.add(map);
        }
        return list;
    }

    /**
     * query permissions by user id
     *
     * @param adminUserId
     * @return
     */
    public List<Permission> findByAdminUserId(int adminUserId) {
        User user = userService.findById(adminUserId);
        List<Permission> permissions = new ArrayList<>();
        if (user.getRoles().size() > 0) {
            user.getRoles().stream().filter(role -> role.getPermissions().size() > 0).forEach(role -> {
                permissions.addAll(role.getPermissions().stream().filter(permission -> permission.getPid() > 0).collect(Collectors.toList()));
            });
        }
        return permissions;
    }

    public void save(Permission permission) {
        permissionDao.save(permission);
    }

    /**
     * delete permission
     * if parent node id not equals 0, delete all children of the parent node id
     *
     * @param id
     */
    public void deleteById(Integer id) {
        Permission permission = findById(id);
        if (permission.getPid() == 0) {
            permissionDao.deleteByPid(permission.getId());
        }
        permissionDao.delete(permission);
    }

    public Permission findById(int id) {
        return permissionDao.findOne(id);
    }

    @CacheEvict("permissions")
    public void clearCache() {}
}
