package co.yiiu.pybbs.config;

import co.yiiu.pybbs.directive.*;
import co.yiiu.pybbs.util.BaseModel;
import co.yiiu.pybbs.util.LocaleMessageSourceUtil;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.annotation.PostConstruct;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Configuration
public class FreemarkerConfig {

  private Logger log = LoggerFactory.getLogger(FreeMarkerConfig.class);

  @Autowired
  private freemarker.template.Configuration configuration;
  @Autowired
  private TopicListDirective topicListDirective;
  @Autowired
  private OtherTopicDirective otherTopicDirective;
  @Autowired
  private NotificationsDirective notificationsDirective;
  @Autowired
  private ScoreDirective scoreDirective;
  @Autowired
  private SearchDirective searchDirective;
  @Autowired
  private TagsDirective tagsDirective;
  @Autowired
  private UserTopicsDirective userTopicsDirective;
  @Autowired
  private UserCommentsDirective userCommentsDirective;
  @Autowired
  private UserCollectsDirective userCollectsDirective;
  @Autowired
  private TopicCommentsDirective topicCommentsDirective;
  @Autowired
  private BaseModel baseModel;
  @Autowired
  private ShiroTag shiroTag;
  @Autowired
  private LocaleMessageSourceUtil localeMessageSourceUtil;

  @PostConstruct
  public void setSharedVariable() throws TemplateModelException {
    //注入全局配置到freemarker
    log.info("开始配置freemarker全局变量...");
    configuration.setSharedVariable("model", baseModel);
    // shiro鉴权
    configuration.setSharedVariable("sec", shiroTag);
    log.info("freemarker全局变量配置完成!");

    log.info("开始配置freemarker自定义标签...");
    configuration.setSharedVariable("tag_topics", topicListDirective);
    configuration.setSharedVariable("tag_other_topic", otherTopicDirective);
    configuration.setSharedVariable("tag_notifications", notificationsDirective);
    configuration.setSharedVariable("tag_score", scoreDirective);
    configuration.setSharedVariable("tag_search", searchDirective);
    configuration.setSharedVariable("tag_tags", tagsDirective);
    configuration.setSharedVariable("tag_user_topics", userTopicsDirective);
    configuration.setSharedVariable("tag_user_comments", userCommentsDirective);
    configuration.setSharedVariable("tag_user_collects", userCollectsDirective);
    configuration.setSharedVariable("tag_topic_comments", topicCommentsDirective);

    configuration.setSharedVariable("i18n", localeMessageSourceUtil);
    log.info("freemarker自定义标签配置完成!");
  }

}
