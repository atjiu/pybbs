package cn.tomoya.module.api.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.ErrorCode;
import cn.tomoya.exception.Result;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseController {

  @Autowired
  private NotificationService notificationService;

  /**
   * 查询当前用户未读的消息数量
   *
   * @return
   */
  @GetMapping("/notRead")
  public Result notRead(String token) throws ApiException {
    User user = getUser(token);
    if (user == null)
      throw new ApiException(ErrorCode.notLogin, "请先登录");
    return Result.success(notificationService.countByTargetUserAndIsRead(user, false));
  }

}
