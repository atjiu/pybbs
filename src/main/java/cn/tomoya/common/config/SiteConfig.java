package cn.tomoya.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ConfigurationProperties(locations = "classpath:config.yml", prefix = "site")
@Setter
@Getter
public class SiteConfig {

    private String name;
    private String baseUrl;
    private String cookieName;
    private String adminCookieName;
    private String cookieDomain;
    private String sessionName;
    private String adminSessionName;
    private int cookieExpireTime;
    private int pageSize;
    private String avatarPath;
    private String uploadPath;
    private List<String> sections;
    private String theme;
    private String beforeUrl;
    private boolean elastic;

}
