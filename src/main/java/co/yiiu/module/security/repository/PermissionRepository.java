package co.yiiu.module.security.repository;

import co.yiiu.module.security.model.Permission;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
@CacheConfig(cacheNames = "permissions")
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

  @Cacheable
  Permission findById(int id);

  @Cacheable
  List<Permission> findByPidGreaterThan(int pid);

  List<Permission> findByPid(int pid);

  void deleteByPid(int pid);

  @CacheEvict
  void delete(Permission permission);
}
