package co.yiiu.pybbs.config;

import co.yiiu.pybbs.config.service.ElasticSearchService;
import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;

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
  @Autowired
  private Environment environment;

  @Override
  public void run(String... args) throws FileNotFoundException {
    boolean search = systemConfigService.selectAllConfig().get("search").toString().equals("1");
    if (search) {
      if (!elasticSearchService.existIndex()) {
        elasticSearchService.createIndex("topic", ElasticSearchService.topicMappingBuilder);
        log.info("创建es索引成功!");
      }
    }

    // 获取templates/theme里所有的主题，更新system_config表中的theme数据
    String activeEnv = null;
    String[] activeProfiles = environment.getActiveProfiles();
    if (activeProfiles.length > 0) {
      activeEnv = activeProfiles[0];
    }
    File file = null;
    if ("dev".equals(activeEnv) || "docker".equals(activeEnv)) {
      file = ResourceUtils.getFile("classpath:templates/theme");
    } else if ("prod".equals(activeEnv)) {
      file = ResourceUtils.getFile("file:./templates/theme");
    }
    if (file != null) {
      File[] files = file.listFiles();
      String[] themes = new String[files.length];
      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          themes[i] = files[i].getName();
        }
      }
      SystemConfig systemConfig = systemConfigService.selectByKey("theme");
      systemConfig.setOption(StringUtils.arrayToCommaDelimitedString(themes));
      systemConfigService.updateByKey("theme", systemConfig);
    }
  }
}
