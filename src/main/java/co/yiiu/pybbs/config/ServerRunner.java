package co.yiiu.pybbs.config;

import co.yiiu.pybbs.config.service.ElasticSearchService;
import co.yiiu.pybbs.service.SystemConfigService;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
@Order(1)
public class ServerRunner implements CommandLineRunner {

  @Autowired
  private ElasticSearchService elasticSearchService;
  @Autowired
  private SystemConfigService systemConfigService;

  @Override
  public void run(String... args) {
    Boolean search = systemConfigService.selectAllConfig().get("search").toString().equals("1");
    if (search) {
      if (!elasticSearchService.existIndex()) {
        elasticSearchService.createIndex("topic", ElasticSearchService.topicMappingBuilder);
      }
    }
  }
}
