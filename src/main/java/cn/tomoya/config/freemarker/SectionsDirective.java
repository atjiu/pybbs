package cn.tomoya.config.freemarker;

import cn.tomoya.module.section.entity.Section;
import cn.tomoya.module.section.service.SectionService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya on 17-6-12.
 */
@Component
public class SectionsDirective implements TemplateDirectiveModel {

  @Autowired
  private SectionService sectionService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

    List<Section> sections = sectionService.findAll();

    environment.setVariable("sections", builder.build().wrap(sections));
    templateDirectiveBody.render(environment.getOut());
  }
}