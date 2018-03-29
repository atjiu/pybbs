package co.yiiu.web.front;

import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiAssert;
import co.yiiu.module.collect.model.Collect;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import co.yiiu.module.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/collect")
public class CollectController extends BaseController {

  @Autowired
  private CollectService collectService;
  @Autowired
  private TopicService topicService;

  @GetMapping("/add")
  @ResponseBody
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
  @ResponseBody
  public Result delete(Integer topicId) {
    User user = getApiUser();
    Collect collect = collectService.findByUserIdAndTopicId(user.getId(), topicId);

    ApiAssert.notNull(collect, "你还没收藏这个话题");

    collectService.deleteById(collect.getId());
    return Result.success(collectService.countByTopicId(topicId));
  }
}
