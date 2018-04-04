package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Configuration
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class MailTemplateConfig {

  Map<String, Object> register;
  Map<String, Object> commentTopic;
  Map<String, Object> replyComment;

}
