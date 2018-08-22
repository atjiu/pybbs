package co.yiiu.config;

import co.yiiu.config.properties.SiteConfig;
import co.yiiu.web.interceptor.CommonInterceptor;
import co.yiiu.web.interceptor.PjaxInterceptor;
import co.yiiu.web.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Autowired
  private CommonInterceptor commonInterceptor;
  @Autowired
  private UserInterceptor userInterceptor;
  @Autowired
  private PjaxInterceptor pjaxInterceptor;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseSuffixPatternMatch(false);
  }

  /**
   * Add intercepter
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(commonInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/api/**", "/static/**", "/common/**", "/admin/**");
    registry.addInterceptor(userInterceptor).addPathPatterns(
        "/topic/create",
        "/comment/save",
        "/notification/list",
        "/user/setting/*"
    );
    registry.addInterceptor(pjaxInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/api/**", "/static/**", "/common/**");
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    super.addResourceHandlers(registry);
    registry.addResourceHandler("/static/**").addResourceLocations(
        "file:./views/" + siteConfig.getTheme() + "/static/",
        "classpath:/" + siteConfig.getTheme() + "/static/");
  }
}
