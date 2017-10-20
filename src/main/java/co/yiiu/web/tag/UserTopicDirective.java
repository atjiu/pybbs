package co.yiiu.web.tag;

import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class UserTopicDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
  @Autowired
  private TopicService topicService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    String username = map.get("username").toString();
    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
    int limit = Integer.parseInt(map.get("limit").toString());

    User currentUser = userService.findByUsername(username);
    Page<Topic> page = topicService.findByUser(p, limit, currentUser);

    environment.setVariable("page", builder.build().wrap(page));
    environment.setVariable("currentUser", builder.build().wrap(currentUser));
    templateDirectiveBody.render(environment.getOut());
  }
}
