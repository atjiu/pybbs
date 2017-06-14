package cn.tomoya.config.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@ConfigurationProperties(prefix = "site")
public class SiteConfig {

  private String name;
  private String intro;
  private String baseUrl;
  private String staticUrl;
  private int pageSize;
  private String uploadPath;
  private String theme;
  private boolean search;
  private int attempts;
  private long attemptsWaitTime;
  private int maxCreateTopic;
  private long userUploadSpaceSize;

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

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
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

  public int getAttempts() {
    return attempts;
  }

  public void setAttempts(int attempts) {
    this.attempts = attempts;
  }

  public long getAttemptsWaitTime() {
    return attemptsWaitTime;
  }

  public void setAttemptsWaitTime(long attemptsWaitTime) {
    this.attemptsWaitTime = attemptsWaitTime;
  }

  public int getMaxCreateTopic() {
    return maxCreateTopic;
  }

  public void setMaxCreateTopic(int maxCreateTopic) {
    this.maxCreateTopic = maxCreateTopic;
  }

  public long getUserUploadSpaceSize() {
    return userUploadSpaceSize;
  }

  public void setUserUploadSpaceSize(long userUploadSpaceSize) {
    this.userUploadSpaceSize = userUploadSpaceSize;
  }
}
