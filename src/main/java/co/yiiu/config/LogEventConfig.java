package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class LogEventConfig {

  private Map<String, String> template = new HashMap<>();

}
