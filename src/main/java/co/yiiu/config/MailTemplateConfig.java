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

  Map register;
  Map commentTopic;
  Map replyComment;

}
