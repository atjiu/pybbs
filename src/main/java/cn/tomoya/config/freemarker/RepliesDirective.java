package cn.tomoya.config.freemarker;

import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    int topicId = Integer.parseInt(map.get("id").toString());
    Topic topic = new Topic();
    topic.setId(topicId);

    List<Reply> replies =replyService.findByTopic(topic);

    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
    environment.setVariable("replies", builder.build().wrap(replies));
    templateDirectiveBody.render(environment.getOut());
  }
}