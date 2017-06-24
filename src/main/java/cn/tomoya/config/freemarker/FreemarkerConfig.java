package cn.tomoya.config.freemarker;

import cn.tomoya.config.base.BaseEntity;
import cn.tomoya.config.security.SpringSecurityTag;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.section.service.SectionService;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FreemarkerConfig {

  Logger log = LoggerFactory.getLogger(FreemarkerConfig.class);

  @Autowired
  private freemarker.template.Configuration configuration;
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
  @Autowired
  private TopicsDirective topicsDirective;
  @Autowired
  private UserDirective userDirective;
  @Autowired
  private CurrentUserDirective currentUserDirective;
  @Autowired
  private NotificationsDirective notificationsDirective;
  @Autowired
  private RepliesDirective repliesDirective;
  @Autowired
  private OtherTopicsDirective otherTopicsDirective;
  @Autowired
  private ScoreDirective scoreDirective;
  @Autowired
  private LabelsDirective labelsDirective;
  @Autowired
  private LabelDirective labelDirective;
  @Autowired
  private BaseEntity baseEntity;
  @Autowired
  private SectionsDirective sectionsDirective;

  @PostConstruct
  public void setSharedVariable() throws TemplateModelException {
    configuration.setSharedVariable("sec", springSecurityTag);
    // 注入全局配置至freemarker
    configuration.setSharedVariable("site", siteConfig);
    configuration.setSharedVariable("model", baseEntity);

    configuration.setSharedVariable("user_topics_tag", userTopicDirective);
    configuration.setSharedVariable("user_replies_tag", userReplyDirective);
    configuration.setSharedVariable("user_collects_tag", userCollectDirective);
    configuration.setSharedVariable("topics_tag", topicsDirective);
    configuration.setSharedVariable("user_tag", userDirective);
    configuration.setSharedVariable("current_user_tag", currentUserDirective);
    configuration.setSharedVariable("notifications_tag", notificationsDirective);
    configuration.setSharedVariable("replies_tag", repliesDirective);
    configuration.setSharedVariable("other_topics_tag", otherTopicsDirective);
    configuration.setSharedVariable("score_tag", scoreDirective);
    configuration.setSharedVariable("labels_tag", labelsDirective);
    configuration.setSharedVariable("label_tag", labelDirective);
    configuration.setSharedVariable("sections_tag", sectionsDirective);

    log.info("init freemarker sharedVariables {site} success...");
  }

}