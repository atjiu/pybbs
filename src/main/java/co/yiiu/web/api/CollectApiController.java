package co.yiiu.web.api;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.module.collect.model.Collect;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api/collect")
public class CollectApiController extends BaseController {

  @Autowired
  private CollectService collectService;
  @Autowired
  private TopicService topicService;

  @GetMapping("/add")
  public Result add(Integer topicId) {
    User user = getApiUser();
    Topic topic = topicService.findById(topicId);

    ApiAssert.notNull(topic, "话题不存在");

    Collect collect = collectService.findByUserIdAndTopicId(getUser().getId(), topicId);
    ApiAssert.isNull(collect, "你已经收藏了这个话题");

    collectService.createCollect(topic, user.getId());

    return Result.success(collectService.countByTopicId(topicId));
  }

  @GetMapping("/delete")
  public Result delete(Integer topicId) {
    User user = getApiUser();
    Collect collect = collectService.findByUserIdAndTopicId(user.getId(), topicId);

    ApiAssert.notNull(collect, "你还没收藏这个话题");

    collectService.deleteById(collect.getId());
    return Result.success(collectService.countByTopicId(topicId));
  }
}
