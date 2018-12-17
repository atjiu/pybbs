package co.yiiu.pybbs.util;

import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Set;

/**
 * Created by tomoya at 2018/12/17
 */
@Component
public class RedisUtil {

  @Autowired
  private SystemConfigService systemConfigService;
  private Jedis jedis;
  private Logger log = LoggerFactory.getLogger(RedisUtil.class);

  public void setJedis(Jedis jedis) {
    this.jedis = jedis;
  }

  public Jedis instance() {
    try {
      if (this.jedis != null) return this.jedis;
      // 获取redis的连接
      // host
      SystemConfig systemConfigHost = systemConfigService.selectByKey("redis.host");
      String host = systemConfigHost.getValue();
      // port
      SystemConfig systemConfigPort = systemConfigService.selectByKey("redis.port");
      String port = systemConfigPort.getValue();
      // password
      SystemConfig systemConfigPassword = systemConfigService.selectByKey("redis.password");
      String password = systemConfigPassword.getValue();
      password = StringUtils.isEmpty(password) ? null : password;
      // database
      SystemConfig systemConfigDatabase = systemConfigService.selectByKey("redis.database");
      String database = systemConfigDatabase.getValue();
      // timeout
      SystemConfig systemConfigTimeout = systemConfigService.selectByKey("redis.timeout");
      String timeout = systemConfigTimeout.getValue();
      // ssl
      SystemConfig systemConfigSSL = systemConfigService.selectByKey("redis.ssl");
      String ssl = systemConfigSSL.getValue();

      if (StringUtils.isEmpty(host)
          || StringUtils.isEmpty(port)
          || StringUtils.isEmpty(database)
          || StringUtils.isEmpty(timeout)) {
        log.info("redis配置信息不全或没有配置...");
        return null;
      }
      JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      // 配置jedis连接池最多空闲多少个实例，源码默认 8
      jedisPoolConfig.setMaxIdle(8);
      // 配置jedis连接池最多创建多少个实例，源码默认 8
      jedisPoolConfig.setMaxTotal(8);
      JedisPool jedisPool = new JedisPool(
          jedisPoolConfig,
          host,
          Integer.parseInt(port),
          Integer.parseInt(timeout),
          password,
          Integer.parseInt(database),
          null,
          ssl.equals("1")
      );
      this.jedis = jedisPool.getResource();
      log.info("redis连接对象获取成功...");
      return this.jedis;
    } catch (Exception e) {
      log.error("配置redis连接池报错，错误信息: {}", e.getMessage());
      return null;
    }
  }

  // 获取String值
  public String getString(String key) {
    Jedis instance = this.instance();
    if (StringUtils.isEmpty(key) || instance == null) return null;
    return instance.get(key);
  }

  public void setString(String key, String value) {
    Jedis instance = this.instance();
    if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value) || instance == null) return;
    instance.set(key, value); // 返回值成功是 OK
  }

  public void delString(String key) {
    Jedis instance = this.instance();
    if (StringUtils.isEmpty(key) || instance == null) return;
    instance.del(key); // 返回值成功是 1
  }

  // TODO 后面会补充获取 list, map 等方法

}
