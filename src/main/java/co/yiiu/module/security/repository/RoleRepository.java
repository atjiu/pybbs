package co.yiiu.module.security.repository;

import co.yiiu.module.security.model.Role;
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
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Role findById(int id);

  List<Role> findAll();

  void delete(Role role);
}
