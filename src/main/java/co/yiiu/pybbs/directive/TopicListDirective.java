package co.yiiu.pybbs.directive;

import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.util.MyPage;
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
public class TopicListDirective implements TemplateDirectiveModel {

  @Autowired
  private TopicService topicService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    Integer pageNo = Integer.parseInt(map.get("pageNo").toString());
    String tab = map.get("tab").toString();
    MyPage<Map<String, Object>> page = topicService.selectAll(pageNo, tab);

    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}
