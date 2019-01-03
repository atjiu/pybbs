package co.yiiu.pybbs.config;

import co.yiiu.pybbs.config.service.ElasticSearchService;
import co.yiiu.pybbs.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class ServerRunner implements CommandLineRunner {

  private Logger log = LoggerFactory.getLogger(ServerRunner.class);

  @Autowired
  private ElasticSearchService elasticSearchService;
  @Autowired
  private SystemConfigService systemConfigService;

  @Override
  public void run(String... args) {
    boolean search = systemConfigService.selectAllConfig().get("search").toString().equals("1");
    if (search) {
      if (!elasticSearchService.existIndex()) {
        elasticSearchService.createIndex("topic", ElasticSearchService.topicMappingBuilder);
        log.info("创建es索引成功!");
      }
    }
  }
}
