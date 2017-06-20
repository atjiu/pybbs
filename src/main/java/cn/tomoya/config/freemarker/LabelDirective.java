package cn.tomoya.config.freemarker;

import cn.tomoya.module.label.entity.Label;
import cn.tomoya.module.label.service.LabelService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 17-6-12.
 */
@Component
public class LabelDirective implements TemplateDirectiveModel {

  @Autowired
  private LabelService labelService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    List<Label> list = new ArrayList<>();

    if(map.get("id") != null) {
      String ids = map.get("id").toString();
      if (ids.split(",").length > 0) {
        for (String id : ids.split(",")) {
          Label label = labelService.findById(Integer.parseInt(id));
          list.add(label);
        }
      } else {
        Label label = labelService.findById(Integer.parseInt(ids));
        list.add(label);
      }
    }

    environment.setVariable("list", builder.build().wrap(list));
    templateDirectiveBody.render(environment.getOut());
  }
}