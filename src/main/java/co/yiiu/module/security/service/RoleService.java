package co.yiiu.module.security.service;

import co.yiiu.module.security.model.Role;
import co.yiiu.module.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  /**
   * 查询所有的角色
   *
   * @return
   */
  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  /**
   * 删除角色
   *
   * @param id
   */
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
  public Role findById(int id) {
    return roleRepository.findById(id);
  }

  public void save(Role role) {
    roleRepository.save(role);
  }

}
