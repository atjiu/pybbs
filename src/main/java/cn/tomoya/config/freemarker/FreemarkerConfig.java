package cn.tomoya.config.freemarker;

import javax.annotation.PostConstruct;

import cn.tomoya.config.security.SpringSecurityTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.section.service.SectionService;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

@Component
public class FreemarkerConfig {

  Logger log = LoggerFactory.getLogger(FreemarkerConfig.class);

  @Autowired
  private Configuration configuration;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private SpringSecurityTag springSecurityTag;
  @Autowired
  private SectionService sectionService;
  @Autowired
  private UserTopicDirective userTopicDirective;
  @Autowired
  private UserReplyDirective userReplyDirective;
  @Autowired
  private UserCollectDirective userCollectDirective;

  @PostConstruct
  public void setSharedVariable() throws TemplateModelException {
    configuration.setSharedVariable("sec", springSecurityTag);
    // 注入全局配置至freemarker
    configuration.setSharedVariable("site", siteConfig);
    // 将版块注入到全局freemarker变量里
    configuration.setSharedVariable("sections", sectionService.findAll());

    configuration.setSharedVariable("user_topics_tag", userTopicDirective);
    configuration.setSharedVariable("user_replies_tag", userReplyDirective);
    configuration.setSharedVariable("user_collects_tag", userCollectDirective);

    log.info("init freemarker sharedVariables {site} success...");
  }

}