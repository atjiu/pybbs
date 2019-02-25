package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.Collect;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.CollectService;
import co.yiiu.pybbs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
  @PostMapping("/{topicId}")
  public Result get(@PathVariable Integer topicId) {
    User user = getApiUser();
    Collect collect = collectService.selectByTopicIdAndUserId(topicId, user.getId());
    ApiAssert.isNull(collect, "做人要知足，每人每个话题只能收藏一次哦！");
    collectService.insert(topicId, user);
    return success();
  }

  // 取消收藏
  @DeleteMapping("/{topicId}")
  public Result delete(@PathVariable Integer topicId) {
    User user = getApiUser();
    Collect collect = collectService.selectByTopicIdAndUserId(topicId, user.getId());
    ApiAssert.notNull(collect, "你都没有收藏这个话题，哪来的取消？");
    collectService.delete(topicId, user.getId());
    return success();
  }
}
