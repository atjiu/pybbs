package co.yiiu.pybbs.service;

import co.yiiu.pybbs.config.service.RedisService;
import co.yiiu.pybbs.mapper.SystemConfigMapper;
import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.util.Constants;
import co.yiiu.pybbs.util.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Service
@Transactional
public class SystemConfigService {

  @Autowired
  private SystemConfigMapper systemConfigMapper;
  @Autowired
  private RedisService redisService;

  private static Map SYSTEM_CONFIG;

  public Map selectAllConfig() {
    if (SYSTEM_CONFIG != null) return SYSTEM_CONFIG;
    String system_config = redisService.getString(Constants.REDIS_SYSTEM_CONFIG_KEY);
    if (system_config != null) {
      SYSTEM_CONFIG = JsonUtil.jsonToObject(system_config, Map.class);
    } else {
      List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
      SYSTEM_CONFIG = systemConfigs
          .stream()
          .filter(systemConfig -> systemConfig.getPid() != 0)
          .collect(Collectors.toMap(SystemConfig::getKey, SystemConfig::getValue));
      // 将查询出来的数据放到redis里缓存下来（如果redis可用的话）
      redisService.setString(Constants.REDIS_SYSTEM_CONFIG_KEY, JsonUtil.objectToJson(SYSTEM_CONFIG));
    }
    return SYSTEM_CONFIG;
  }

  // 根据键取值
  public SystemConfig selectByKey(String key) {
    QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
    wrapper.lambda()
        .eq(SystemConfig::getKey, key);
    return systemConfigMapper.selectOne(wrapper);
  }

  public Map<String, Object> selectAll() {
    Map<String, Object> map = new LinkedHashMap<>();
    List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
    // 先提取出所有父节点
    List<SystemConfig> p = systemConfigs
        .stream()
        .filter(systemConfig -> systemConfig.getPid() == 0)
        .collect(Collectors.toList());
    // 遍历父节点取父节点下的所有子节点
    p.forEach(systemConfig -> {
      List<SystemConfig> collect = systemConfigs
          .stream()
          .filter(systemConfig1 -> systemConfig1.getPid().equals(systemConfig.getId()))
          .collect(Collectors.toList());
      map.put(systemConfig.getDescription(), collect);
    });
    return map;
  }

  // 在更新系统设置后，清一下selectAllConfig()的缓存
  public void update(List<Map<String, String>> list) {
    for (Map map: list) {
      SystemConfig systemConfig = new SystemConfig();
      systemConfig.setKey((String) map.get("name"));
      systemConfig.setValue((String) map.get("value"));
      QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
      wrapper.lambda().eq(SystemConfig::getKey, systemConfig.getKey());
      systemConfigMapper.update(systemConfig, wrapper);
    }
    // 判断redis配置是否去除，去除了，就将RedisUtil里的jedis属性设置为null
    if (!redisService.isRedisConfig()) redisService.setJedis(null);
    // 清除redis里关于 system_config 的缓存
    redisService.delString(Constants.REDIS_SYSTEM_CONFIG_KEY);
    // 更新SYSTEM_CONFIG
    SYSTEM_CONFIG = null;
  }

  // 根据key更新数据
  public void updateByKey(String key, SystemConfig systemConfig) {
    QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(SystemConfig::getKey, key);
    systemConfigMapper.update(systemConfig, wrapper);
    // 清除redis里关于 system_config 的缓存
    redisService.delString(Constants.REDIS_SYSTEM_CONFIG_KEY);
    // 更新SYSTEM_CONFIG
    SYSTEM_CONFIG = null;
  }

}
