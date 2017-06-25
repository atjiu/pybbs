package cn.tomoya.config.freemarker;

import cn.tomoya.module.label.service.LabelService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya on 17-6-12.
 */
@Component
public class LabelsDirective implements TemplateDirectiveModel {

  @Autowired
  private LabelService labelService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    int p = map.get("p") == null ? 1 : Integer.parseInt(map.get("p").toString());
    int size = map.get("size") == null ? 30 : Integer.parseInt(map.get("size").toString());
    Page page = labelService.findAll(p, size);

    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}