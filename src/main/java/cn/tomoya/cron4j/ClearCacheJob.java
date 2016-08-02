package cn.tomoya.cron4j;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class ClearCacheJob implements Runnable {
    @Override
    public void run() {
        LogKit.info("开始清理缓存");
        Cache cache = Redis.use();
        cache.getJedis().flushDB();
        LogKit.info("缓存清理完成");
    }
}
