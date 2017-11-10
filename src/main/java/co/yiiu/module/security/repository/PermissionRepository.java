package co.yiiu.module.security.repository;

import co.yiiu.module.security.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

  Permission findByName(String name);

  Permission findById(int id);

  List<Permission> findByPidGreaterThan(int pid);

  List<Permission> findByPid(int pid);

  void deleteByPid(int pid);

  void delete(Permission permission);
}
