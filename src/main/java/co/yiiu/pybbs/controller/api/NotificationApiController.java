package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.service.NotificationService;
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
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseApiController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping("/notRead")
  public Result notRead() {
    return success(notificationService.countNotRead(getUser().getId()));
  }
}
