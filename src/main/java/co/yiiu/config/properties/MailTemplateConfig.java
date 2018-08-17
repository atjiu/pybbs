package co.yiiu.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailTemplateConfig {

  Map<String, Object> register;
  Map<String, Object> commentTopic;
  Map<String, Object> replyComment;

  public Map<String, Object> getRegister() {
    return register;
  }

  public void setRegister(Map<String, Object> register) {
    this.register = register;
  }

  public Map<String, Object> getCommentTopic() {
    return commentTopic;
  }

  public void setCommentTopic(Map<String, Object> commentTopic) {
    this.commentTopic = commentTopic;
  }

  public Map<String, Object> getReplyComment() {
    return replyComment;
  }

  public void setReplyComment(Map<String, Object> replyComment) {
    this.replyComment = replyComment;
  }
}
