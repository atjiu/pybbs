package co.yiiu.pybbs.directive;

import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.service.TagService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
public class TagsDirective implements TemplateDirectiveModel {

  @Autowired
  private TagService tagService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    Integer pageNo = Integer.parseInt(map.get("pageNo").toString());
    Integer pageSize = Integer.parseInt(map.get("pageSize").toString());
    IPage<Tag> page = tagService.selectAll(pageNo, pageSize, null);

    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}
