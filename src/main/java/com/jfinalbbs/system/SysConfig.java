package com.jfinalbbs.system;

import com.jfinal.plugin.activerecord.Model;
import com.jfinalbbs.common.Constants;
import com.jfinalbbs.utils.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class SysConfig extends Model<SysConfig> {

    public final static SysConfig me = new SysConfig();

    /**
     * 根据key查询value,并放入缓存里
     * @param key
     * @return
     */
    public String findByKey(String key) {
        Map<String, Object> map = findAll2Map();
        return map.get(key).toString();
    }

    public Map<String, Object> findAll2Map() {
        List<SysConfig> list = super.findByCache(
                Constants.SYSCONFIGCACHE,
                Constants.SYSCONFIGCACHEKEY,
                "select * from sys_config"
        );
        Map<String, Object> map = new HashMap<String, Object>();
        for(SysConfig sc: list) {
            map.put(sc.getStr("key"), sc.getStr("value"));
        }
        return map;
    }

    public void update(String key, String value) {
        if(!StrUtil.isBlank(value)) {
            SysConfig sysConfig = findFirst("select * from sys_config where `key` = ?", key);
            if (sysConfig != null) {
                sysConfig.set("value", value);
                sysConfig.update();
            }
        }
    }
}
