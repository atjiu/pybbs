package co.yiiu.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Getter
@Setter
public class MailTemplateConfig {

  Map<String, Object> register;
  Map<String, Object> commentTopic;
  Map<String, Object> replyComment;

}