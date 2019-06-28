package co.yiiu.pybbs.config;

import co.yiiu.pybbs.plugin.ElasticSearchService;
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

  @Override
  public void run(String... args) {
    if (elasticSearchService.instance() != null && !elasticSearchService.existIndex()) {
      if (elasticSearchService.createIndex("topic", ElasticSearchService.topicMappingBuilder)) {
        log.info("创建es索引成功!");
      } else {
        log.info("创建es索引失败!");
      }
    }
  }
}
