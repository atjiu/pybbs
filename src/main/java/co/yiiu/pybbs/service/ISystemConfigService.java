package co.yiiu.pybbs.service;

import co.yiiu.pybbs.model.SystemConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
public interface ISystemConfigService {
    Map selectAllConfig();

    // 根据键取值
    SystemConfig selectByKey(String key);

    Map<String, Object> selectAll();

    // 在更新系统设置后，清一下selectAllConfig()的缓存
    void update(List<Map<String, String>> list);

    // 根据key更新数据
    void updateByKey(String key, SystemConfig systemConfig);
}
