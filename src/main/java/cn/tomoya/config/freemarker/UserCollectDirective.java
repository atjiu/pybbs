package cn.tomoya.config.freemarker;

import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.collect.entity.Collect;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya on 17-6-6.
 */
@Component
public class UserCollectDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
  @Autowired
  private CollectService collectService;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    String username = map.get("username").toString();
    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());

    User currentUser = userService.findByUsername(username);
    Page<Collect> page = collectService.findByUser(p, siteConfig.getPageSize(), currentUser);

    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
    environment.setVariable("page", builder.build().wrap(page));
    environment.setVariable("currentUser", builder.build().wrap(currentUser));
    templateDirectiveBody.render(environment.getOut());
  }
}
