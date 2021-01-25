package co.yiiu.pybbs.config;

import co.yiiu.pybbs.util.SpringContextUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class DataSourceConfig {

    @Resource
    private SiteConfig siteConfig;

    private HikariDataSource dataSource;

    public HikariDataSource instance() {
        if (siteConfig == null) siteConfig = SpringContextUtil.getBean(SiteConfig.class);
        if (dataSource != null) return dataSource;
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(siteConfig.getDatasource_driver());
        config.setJdbcUrl(siteConfig.getDatasource_url());
        config.setUsername(siteConfig.getDatasource_username());
        config.setPassword(siteConfig.getDatasource_password());
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
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setMaximumPoolSize(100);
//        dataSource.setMinimumIdle(10);
//        dataSource.setDataSourceClassName("oracle.jdbc.pool.OracleDataSource");
//
//        Properties properties = new Properties();
//        properties.put("user", "");
//        properties.put("password", "");
//        properties.put("databaseName", "");
//        properties.put("serverName", "192.168.xx.xx");
//        properties.put("portNumber", "1521");
//        properties.put("driverType", "thin");
//        dataSource.setDataSourceProperties(properties);
        return dataSource;
    }

    @Bean(name = "dataSource")
    @DependsOn("dataSourceHelper")
    public DataSource dataSource() {
        return instance();
    }
}
