package co.yiiu.config;

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
  private int pageSize;
  private String uploadPath;
  private String theme;
  private boolean search;
  private int attempts;
  private long attemptsWaitTime;
  private int maxCreateTopic;
  private int createTopicScore;
  private int createReplyScore;
  private long userUploadSpaceSize;
  private int score;
  private int loginPoints;
  private String GA;
  private String googleZZ;
  private String baiduTJ;
  private String baiduZZ;
  private boolean ssl;
  private String newUserRole;
  private CookieConfig cookie;

  private MailTemplateConfig mail;

  public boolean isSsl() {
    return ssl;
  }

  public void setSsl(boolean ssl) {
    this.ssl = ssl;
  }

  public int getLoginPoints() {
    return loginPoints;
  }

  public void setLoginPoints(int loginPoints) {
    this.loginPoints = loginPoints;
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

  public String getGA() {
    return GA;
  }

  public void setGA(String GA) {
    this.GA = GA;
  }

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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getNewUserRole() {
    return newUserRole;
  }

  public void setNewUserRole(String newUserRole) {
    this.newUserRole = newUserRole;
  }

  public CookieConfig getCookie() {
    return cookie;
  }

  public void setCookie(CookieConfig cookie) {
    this.cookie = cookie;
  }

  public int getCreateTopicScore() {
    return createTopicScore;
  }

  public void setCreateTopicScore(int createTopicScore) {
    this.createTopicScore = createTopicScore;
  }

  public int getCreateReplyScore() {
    return createReplyScore;
  }

  public void setCreateReplyScore(int createReplyScore) {
    this.createReplyScore = createReplyScore;
  }

  public MailTemplateConfig getMail() {
    return mail;
  }

  public void setMail(MailTemplateConfig mail) {
    this.mail = mail;
  }
}
