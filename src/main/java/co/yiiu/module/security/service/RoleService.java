package co.yiiu.module.security.service;

import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "roles")
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }

  /**
   * 查询所有的角色
   *
   * @return
   */
  @Cacheable
  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  /**
   * 删除角色
   *
   * @param id
   */
  @CacheEvict(allEntries = true)
  public void deleteById(Integer id) {
    Role role = findById(id);
    roleRepository.delete(role);
  }

  /**
   * 根据id查找角色
   *
   * @param id
   * @return
   */
  @Cacheable
  public Role findById(int id) {
    return roleRepository.findById(id);
  }

  @CacheEvict(allEntries = true)
  public void save(Role role) {
    roleRepository.save(role);
  }

}
