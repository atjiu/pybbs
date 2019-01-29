package co.yiiu.pybbs.directive;

import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.CollectService;
import co.yiiu.pybbs.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class UserCollectsDirective implements TemplateDirectiveModel {

  @Autowired
  private CollectService collectService;
  @Autowired
  private UserService userService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    String username = String.valueOf(map.get("username"));
    Integer pageNo = Integer.parseInt(map.get("pageNo").toString());
    User user = userService.selectByUsername(username);
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
    environment.setVariable("collects", builder.build().wrap(collectService.selectByUserId(user.getId(), pageNo, null)));
    templateDirectiveBody.render(environment.getOut());
  }
}
