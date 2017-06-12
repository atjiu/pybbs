package cn.tomoya.config.freemarker;

import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya on 17-6-12.
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
    String username = map.get("username").toString();

    User currentUser = userService.findByUsername(username);
    long collectCount = collectService.countByUser(currentUser);

    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
    environment.setVariable("currentUser", builder.build().wrap(currentUser));
    environment.setVariable("collectCount", builder.build().wrap(collectCount));
    templateDirectiveBody.render(environment.getOut());
  }
}