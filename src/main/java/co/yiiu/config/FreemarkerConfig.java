package co.yiiu.config;

import co.yiiu.core.base.BaseEntity;
import co.yiiu.web.tag.*;
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
  private ScoreDirective scoreDirective;
  @Autowired
  private BaseEntity baseEntity;
  @Autowired
  private NodesDirective nodesDirective;
  @Autowired
  private NodeTopicsDirective nodeTopicsDirective;

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
    configuration.setSharedVariable("score_tag", scoreDirective);
    configuration.setSharedVariable("nodes_tag", nodesDirective);
    configuration.setSharedVariable("node_topics_tag", nodeTopicsDirective);

    log.info("init freemarker sharedVariables {site} success...");
  }

}