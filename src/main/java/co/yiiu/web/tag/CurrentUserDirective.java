package co.yiiu.web.tag;

import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.user.model.User;
import co.yiiu.module.user.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class CurrentUserDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
  @Autowired
  private CollectService collectService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    String username = map.get("username").toString();
    User currentUser = userService.findByUsername(username);
    long collectCount = collectService.countByUser(currentUser);

    environment.setVariable("currentUser", builder.build().wrap(currentUser));
    environment.setVariable("collectCount", builder.build().wrap(collectCount));
    templateDirectiveBody.render(environment.getOut());
  }
}