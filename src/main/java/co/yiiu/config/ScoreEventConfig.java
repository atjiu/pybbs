package co.yiiu.config;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Configuration
@ConfigurationProperties(prefix = "score")
public class ScoreEventConfig {

    private Map<String, String> template = Maps.newConcurrentMap();


    public Map<String, String> getTemplate() {
        return template;
    }

    public void setTemplate(Map<String, String> template) {
        this.template = template;
    }
}
