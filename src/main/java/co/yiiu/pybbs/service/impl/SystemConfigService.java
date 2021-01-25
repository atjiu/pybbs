package co.yiiu.pybbs.service.impl;

import co.yiiu.pybbs.mapper.SystemConfigMapper;
import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.ISystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class SystemConfigService implements ISystemConfigService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    private static Map<String, String> SYSTEM_CONFIG;

    @Override
    public Map<String, String> selectAllConfig() {
        if (SYSTEM_CONFIG != null) return SYSTEM_CONFIG;
        List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
        SYSTEM_CONFIG = systemConfigs.stream()
                .filter(systemConfig -> systemConfig.getPid() != 0)
                .collect(Collectors.toMap(SystemConfig::getKey, SystemConfig::getValue));
        return SYSTEM_CONFIG;
    }

    // 根据键取值
    @Override
    public SystemConfig selectByKey(String key) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemConfig::getKey, key);
        return systemConfigMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> selectAll() {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
        // 先提取出所有父节点
        List<SystemConfig> p = systemConfigs.stream().filter(systemConfig -> systemConfig.getPid() == 0).collect
                (Collectors.toList());
        // 遍历父节点取父节点下的所有子节点
        p.forEach(systemConfig -> {
            List<SystemConfig> collect = systemConfigs.stream()
                    .filter(systemConfig1 -> systemConfig1.getPid().equals(systemConfig.getId()))
                    .collect(Collectors.toList());
            map.put(systemConfig.getDescription(), collect);
        });
        return map;
    }

    // 在更新系统设置后，清一下selectAllConfig()的缓存
    @Override
    public void update(List<Map<String, String>> list) {
        for (Map<String, String> map : list) {
            String key = map.get("name");
            String value = map.get("value");
            // 如果密码没有变动，则不做修改
            if ((key.equals("mail_password") && value.equals("*******")) || (key.equals("redis_password") && value.equals
                    ("*******")) || (key.equals("oauth_github_client_secret") && value.equals("*******"))) {
                continue;
            }
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setKey(key);
            systemConfig.setValue(value);
            QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SystemConfig::getKey, systemConfig.getKey());
            systemConfigMapper.update(systemConfig, wrapper);
        }
        // 更新SYSTEM_CONFIG
        SYSTEM_CONFIG = null;
    }

    // 根据key更新数据
    @Override
    public void updateByKey(String key, SystemConfig systemConfig) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemConfig::getKey, key);
        systemConfigMapper.update(systemConfig, wrapper);
        // 更新SYSTEM_CONFIG
        SYSTEM_CONFIG = null;
    }

}
