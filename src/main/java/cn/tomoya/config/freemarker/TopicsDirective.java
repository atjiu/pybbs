package cn.tomoya.config.freemarker;

import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya on 17-6-12.
 */
@Component
public class TopicsDirective implements TemplateDirectiveModel {

  @Autowired
  private TopicService topicService;
  @Autowired
  private SiteConfig siteConfig;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    Boolean lastest = map.get("lastest") != null && Boolean.parseBoolean(map.get("lastest").toString());

    Integer labelId = null;
    if(map.get("labelId") != null) labelId = Integer.parseInt(map.get("labelId").toString());

    String tab = StringUtils.isEmpty(map.get("tab")) ? "全部" : map.get("tab").toString();

    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
    Page<Topic> page = topicService.page(p, siteConfig.getPageSize(), tab, lastest, labelId);

    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}