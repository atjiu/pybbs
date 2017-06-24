package cn.tomoya.config.freemarker;

import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 17-6-12.
 */
@Component
public class RepliesDirective implements TemplateDirectiveModel {

  @Autowired
  private ReplyService replyService;
  @Autowired
  private UserService userService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    int topicId = Integer.parseInt(map.get("id").toString());
    Topic topic = new Topic();
    topic.setId(topicId);

    List<Reply> replies = replyService.findByTopic(topic);
    environment.setVariable("replies", builder.build().wrap(replies));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
      User user = userService.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
      environment.setVariable("user", builder.build().wrap(user));
    }

    templateDirectiveBody.render(environment.getOut());
  }
}