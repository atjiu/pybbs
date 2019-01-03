package co.yiiu.pybbs.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class DataSourceConfig {

  @Value("${datasource_driver}")
  private String driver;
  @Value("${datasource_url}")
  private String url;
  @Value("${datasource_username}")
  private String username;
  @Value("${datasource_password}")
  private String password;

  private HikariDataSource dataSource;

  public HikariDataSource instance() {
    if (dataSource != null) return dataSource;
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(driver);
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.addDataSourceProperty("cachePrepStmts", true);
    config.addDataSourceProperty("prepStmtCacheSize", 500);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    config.setConnectionTestQuery("SELECT 1");
    config.setAutoCommit(true);
    //池中最小空闲链接数量
    config.setMinimumIdle(10);
    //池中最大链接数量
    config.setMaximumPoolSize(50);
    dataSource = new HikariDataSource(config);
    return dataSource;
  }

  @Bean(name = "dataSource")
  @DependsOn("dataSourceHelper")
  public DataSource dataSource() {
    return instance();
  }
}
