package co.yiiu.pybbs.plugin;

import co.yiiu.pybbs.config.service.BaseService;
import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.ISystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@DependsOn("mybatisPlusConfig")
public class RedisService implements BaseService<JedisPool> {

    @Resource
    private ISystemConfigService systemConfigService;
    private JedisPool jedisPool;
    private final Logger log = LoggerFactory.getLogger(RedisService.class);

    public void setJedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPool instance() {
        try {
            if (this.jedisPool != null) return this.jedisPool;
            // 获取redis的连接
            // host
            SystemConfig systemConfigHost = systemConfigService.selectByKey("redis_host");
            String host = systemConfigHost.getValue();
            // port
            SystemConfig systemConfigPort = systemConfigService.selectByKey("redis_port");
            String port = systemConfigPort.getValue();
            // password
            SystemConfig systemConfigPassword = systemConfigService.selectByKey("redis_password");
            String password = systemConfigPassword.getValue();
            password = StringUtils.isEmpty(password) ? null : password;
            // database
            SystemConfig systemConfigDatabase = systemConfigService.selectByKey("redis_database");
            String database = systemConfigDatabase.getValue();
            // timeout
            SystemConfig systemConfigTimeout = systemConfigService.selectByKey("redis_timeout");
            String timeout = systemConfigTimeout.getValue();

            if (!this.isRedisConfig()) {
                log.info("redis配置信息不全或没有配置...");
                return null;
            }
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            // 配置jedis连接池最多空闲多少个实例，源码默认 8
            jedisPoolConfig.setMaxIdle(7);
            // 配置jedis连接池最多创建多少个实例，源码默认 8
            jedisPoolConfig.setMaxTotal(20);
            //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            jedisPoolConfig.setTestOnBorrow(true);
            //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
            jedisPoolConfig.setTestOnReturn(true);
            jedisPool = new JedisPool(jedisPoolConfig, host, Integer.parseInt(port), Integer.parseInt(timeout), password,
                    Integer.parseInt(database));
            log.info("redis连接对象获取成功...");
            return this.jedisPool;
        } catch (Exception e) {
            log.error("配置redis连接池报错，错误信息: {}", e.getMessage());
            return null;
        }
    }

    // 判断redis是否配置了
    public boolean isRedisConfig() {
        SystemConfig systemConfigHost = systemConfigService.selectByKey("redis_host");
        String host = systemConfigHost.getValue();
        // port
        SystemConfig systemConfigPort = systemConfigService.selectByKey("redis_port");
        String port = systemConfigPort.getValue();
        // database
        SystemConfig systemConfigDatabase = systemConfigService.selectByKey("redis_database");
        String database = systemConfigDatabase.getValue();
        // timeout
        SystemConfig systemConfigTimeout = systemConfigService.selectByKey("redis_timeout");
        String timeout = systemConfigTimeout.getValue();

        return !StringUtils.isEmpty(host) && !StringUtils.isEmpty(port) && !StringUtils.isEmpty(database) && !StringUtils
                .isEmpty(timeout);
    }

    // 获取String值
    public String getString(String key) {
        JedisPool instance = this.instance();
        if (StringUtils.isEmpty(key) || instance == null) return null;
        Jedis jedis = instance.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public void setString(String key, String value) {
        this.setString(key, value, 300); // 如果不指定过时时间，默认为5分钟
    }

    /**
     * 带有过期时间的保存数据到redis，到期自动删除
     *
     * @param key
     * @param value
     * @param expireTime 单位 秒
     */
    public void setString(String key, String value, int expireTime) {
        JedisPool instance = this.instance();
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || instance == null) return;
        Jedis jedis = instance.getResource();
        SetParams params = new SetParams();
        params.px(expireTime * 1000);
        jedis.set(key, value, params);
        jedis.close();
    }

    public void delString(String key) {
        JedisPool instance = this.instance();
        if (StringUtils.isEmpty(key) || instance == null) return;
        Jedis jedis = instance.getResource();
        jedis.del(key); // 返回值成功是 1
        jedis.close();
    }

    // TODO 后面有需要会补充获取 list, map 等方法

}
