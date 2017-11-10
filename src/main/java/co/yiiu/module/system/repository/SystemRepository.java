package co.yiiu.module.system.repository;

import co.yiiu.module.system.model.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Repository
public interface SystemRepository extends JpaRepository<System, Integer> {

  System findByName(String name);
}
