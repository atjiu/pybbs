package co.yiiu.web.api;

import co.yiiu.config.SiteConfig;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.module.node.service.NodeService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api")
public class IndexApiController extends BaseController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private NodeService nodeService;

  /**
   * 话题列表接口
   *
   * @param tab 取值范畴 default, newest, good, noanswer
   * @param p
   * @return
   * @throws ApiException
   */
  @GetMapping("/index")
  public Result index(String tab, Integer p) throws ApiException {
    if (!StringUtils.isEmpty(tab) && nodeService.findByName(tab) == null)
      throw new ApiException("版块不存在");
    if (StringUtils.isEmpty(tab))
      tab = "default";
    Page<Topic> page = topicService.page(p == null ? 1 : p, siteConfig.getPageSize(), tab);
    return Result.success(page);
  }
}
