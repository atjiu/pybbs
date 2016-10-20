package cn.tomoya.module.security.dao;

import cn.tomoya.module.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
}
