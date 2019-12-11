package co.yiiu.pybbs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@ConfigurationProperties(value = "site")
public class SiteConfig implements Serializable {

    private static final long serialVersionUID = -7632268193700036274L;

    private String datasource_driver;
    private String datasource_url;
    private String datasource_username;
    private String datasource_password;

    public String getDatasource_driver() {
        return datasource_driver;
    }

    public void setDatasource_driver(String datasource_driver) {
        this.datasource_driver = datasource_driver;
    }

    public String getDatasource_url() {
        return datasource_url;
    }

    public void setDatasource_url(String datasource_url) {
        this.datasource_url = datasource_url;
    }

    public String getDatasource_username() {
        return datasource_username;
    }

    public void setDatasource_username(String datasource_username) {
        this.datasource_username = datasource_username;
    }

    public String getDatasource_password() {
        return datasource_password;
    }

    public void setDatasource_password(String datasource_password) {
        this.datasource_password = datasource_password;
    }
}
