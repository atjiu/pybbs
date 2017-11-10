package co.yiiu.module.system.service;

import co.yiiu.module.system.model.System;
import co.yiiu.module.system.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class SystemService {

  @Autowired
  private SystemRepository systemRepository;

  public System findByName(String name) {
    return systemRepository.findByName(name);
  }

  public System save(System system) {
    return systemRepository.save(system);
  }
}
