package com.jfinalbbs.system;

import com.jfinal.plugin.activerecord.Model;
import com.jfinalbbs.common.Constants;

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
        Map<String, String> map = findAll2Map();
        return map.get(key);
    }

    public Map<String, String> findAll2Map() {
        List<SysConfig> list = super.findByCache(
                Constants.SYSCONFIGCACHE,
                Constants.SYSCONFIGCACHEKEY,
                "select * from sys_config"
        );
        Map<String, String> map = new HashMap<String, String>();
        for(SysConfig sc: list) {
            map.put(sc.getStr("key"), sc.getStr("value"));
        }
        return map;
    }
}
