package co.yiiu.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@ConfigurationProperties(prefix = "site")
public class SiteConfig {

  private String name;
  private String intro;
  private String baseUrl;
  private String staticUrl;
  private Integer pageSize;
  private String uploadPath;
  private String theme;
  private boolean search;
  private String GA;
  private String googleZZ;
  private String baiduTJ;
  private String baiduZZ;
  private String uploadType;
  private Integer loginPoints;
  private Integer attempts;
  private Integer attemptsWaitTime;
  private CookieConfig cookie;
  private OAuth2Config oauth2;
  private UploadConfig upload;
  private SocketConfig socket;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getStaticUrl() {
    return staticUrl;
  }

  public void setStaticUrl(String staticUrl) {
    this.staticUrl = staticUrl;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getUploadPath() {
    return uploadPath;
  }

  public void setUploadPath(String uploadPath) {
    this.uploadPath = uploadPath;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public boolean isSearch() {
    return search;
  }

  public void setSearch(boolean search) {
    this.search = search;
  }

  public String getGA() {
    return GA;
  }

  public void setGA(String GA) {
    this.GA = GA;
  }

  public String getGoogleZZ() {
    return googleZZ;
  }

  public void setGoogleZZ(String googleZZ) {
    this.googleZZ = googleZZ;
  }

  public String getBaiduTJ() {
    return baiduTJ;
  }

  public void setBaiduTJ(String baiduTJ) {
    this.baiduTJ = baiduTJ;
  }

  public String getBaiduZZ() {
    return baiduZZ;
  }

  public void setBaiduZZ(String baiduZZ) {
    this.baiduZZ = baiduZZ;
  }

  public String getUploadType() {
    return uploadType;
  }

  public void setUploadType(String uploadType) {
    this.uploadType = uploadType;
  }

  public Integer getLoginPoints() {
    return loginPoints;
  }

  public void setLoginPoints(Integer loginPoints) {
    this.loginPoints = loginPoints;
  }

  public Integer getAttempts() {
    return attempts;
  }

  public void setAttempts(Integer attempts) {
    this.attempts = attempts;
  }

  public Integer getAttemptsWaitTime() {
    return attemptsWaitTime;
  }

  public void setAttemptsWaitTime(Integer attemptsWaitTime) {
    this.attemptsWaitTime = attemptsWaitTime;
  }

  public CookieConfig getCookie() {
    return cookie;
  }

  public void setCookie(CookieConfig cookie) {
    this.cookie = cookie;
  }

  public OAuth2Config getOauth2() {
    return oauth2;
  }

  public void setOauth2(OAuth2Config oauth2) {
    this.oauth2 = oauth2;
  }

  public UploadConfig getUpload() {
    return upload;
  }

  public void setUpload(UploadConfig upload) {
    this.upload = upload;
  }

  public SocketConfig getSocket() {
    return socket;
  }

  public void setSocket(SocketConfig socket) {
    this.socket = socket;
  }
}
