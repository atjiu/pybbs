package co.yiiu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Configuration
@ConfigurationProperties(prefix = "log")
public class LogEventConfig {

  private Map<String, String> template = new HashMap<>();

  public Map<String, String> getTemplate() {
    return template;
  }

  public void setTemplate(Map<String, String> template) {
    this.template = template;
  }
}
