package cn.tomoya.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ConfigurationProperties(prefix = "site")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getAdminCookieName() {
        return adminCookieName;
    }

    public void setAdminCookieName(String adminCookieName) {
        this.adminCookieName = adminCookieName;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getAdminSessionName() {
        return adminSessionName;
    }

    public void setAdminSessionName(String adminSessionName) {
        this.adminSessionName = adminSessionName;
    }

    public int getCookieExpireTime() {
        return cookieExpireTime;
    }

    public void setCookieExpireTime(int cookieExpireTime) {
        this.cookieExpireTime = cookieExpireTime;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
