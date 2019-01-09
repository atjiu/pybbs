package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.service.CollectService;
import co.yiiu.pybbs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@RestController
@RequestMapping("/api/collect")
public class CollectApiController extends BaseApiController {

  @Autowired
  private CollectService collectService;

  // 收藏话题
  @GetMapping("/get")
  public Result get(Integer topicId) {
    Collect collect = collectService.selectByTopicIdAndUserId(topicId, getApiUser().getId());
    ApiAssert.isNull(collect, "做人要知足，每人每个话题只能收藏一次哦！");
    collectService.insert(topicId, getApiUser());
    return success();
  }

  // 取消收藏
  @GetMapping("/delete")
  public Result delete(Integer topicId) {
    Collect collect = collectService.selectByTopicIdAndUserId(topicId, getApiUser().getId());
    ApiAssert.notNull(collect, "你都没有收藏这个话题，哪来的取消？");
    collectService.delete(topicId, getApiUser().getId());
    return success();
  }
}
