package co.yiiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@SpringBootApplication
// @EnableAutoConfiguration注解加上，有异常不会找默认error页面了，而是直接输出字符串
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableElasticsearchRepositories(basePackages = "co.yiiu.module.es.repository")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
