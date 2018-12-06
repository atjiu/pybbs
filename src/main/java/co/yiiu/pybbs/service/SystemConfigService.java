package co.yiiu.pybbs.service;

import co.yiiu.pybbs.mapper.SystemConfigMapper;
import co.yiiu.pybbs.model.SystemConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
@CacheConfig(cacheNames = "system_config")
public class SystemConfigService {

  @Autowired
  private SystemConfigMapper systemConfigMapper;

  @Cacheable
  public Map<String, Object> selectAllConfig() {
    List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
    Map<String, Object> map = new HashMap<>();
    systemConfigs.forEach(systemConfig -> map.put(systemConfig.getKey(), systemConfig.getValue()));
    return map;
  }

  public List<SystemConfig> selectAll() {
    return systemConfigMapper.selectList(null);
  }

  // 在更新系统设置后，清一下selectAllConfig()的缓存
  // 必须要将这个参数allEntries设置为true，否则清不掉
  @CacheEvict(allEntries = true)
  public void update(SystemConfig systemConfig) {
    QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(SystemConfig::getKey, systemConfig.getKey());
    systemConfigMapper.update(systemConfig, wrapper);
  }
}
