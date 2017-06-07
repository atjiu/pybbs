package cn.tomoya.config.freemarker;

import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by tomoya on 17-6-6.
 */
@Component
public class UserReplyDirective implements TemplateDirectiveModel {

  @Autowired
  private UserService userService;
  @Autowired
  private ReplyService replyService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    Page<Reply> page = new PageImpl<>(new ArrayList<>());
    if (map.containsKey("username") && map.get("username") != null) {
      String username = map.get("username").toString();
      if (map.containsKey("p")) {
        int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
        int limit = Integer.parseInt(map.get("limit").toString());
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
          page = replyService.findByUser(p, limit, currentUser);
        }
      }
    }
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}
