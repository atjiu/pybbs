package co.yiiu.pybbs.directive;

import co.yiiu.pybbs.config.service.ElasticSearchService;
import co.yiiu.pybbs.service.SystemConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class SearchDirective implements TemplateDirectiveModel {

  @Autowired
  private SystemConfigService systemConfigService;
  @Autowired
  private ElasticSearchService elasticSearchService;

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    Page<Map<String, Object>> page = new Page<>();
    String keyword = String.valueOf(map.get("keyword"));
    Integer pageNo = Integer.parseInt(map.get("pageNo").toString());
    if (!StringUtils.isEmpty(keyword)) {
      Integer pageSize = Integer.parseInt(systemConfigService.selectAllConfig().get("page_size").toString());
      page = elasticSearchService.searchDocument(pageNo, pageSize, keyword, "title", "content");
    }

    DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
    environment.setVariable("page", builder.build().wrap(page));
    templateDirectiveBody.render(environment.getOut());
  }
}
