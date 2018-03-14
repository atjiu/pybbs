package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
@ConfigurationProperties(prefix = "site")
@Getter
@Setter
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
  private int createCommentScore;
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
  private List<String> illegalUsername;
  private MailTemplateConfig mail;

}
