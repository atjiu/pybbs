package co.yiiu.web.api;

import co.yiiu.core.ErrorCodeConstant;
import co.yiiu.core.base.BaseController;
import co.yiiu.core.bean.Result;
import co.yiiu.core.exception.ApiException;
import co.yiiu.module.notification.service.NotificationService;
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
      throw new ApiException(ErrorCodeConstant.notLogin, "请先登录");
    return Result.success(notificationService.countByTargetUserAndIsRead(user, false));
  }

}
