package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.NotificationService;
import co.yiiu.pybbs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    User user = getApiUser();
    return success(notificationService.countNotRead(user.getId()));
  }

  @GetMapping("/markRead")
  public Result markRead() {
    User user = getApiUser();
    notificationService.markRead(user.getId());
    return success();
  }

  // 通知列表
  @GetMapping("/list")
  public Result list() {
    User user = getApiUser();
    // 未读消息列表
    List<Map<String, Object>> notReadNotifications = notificationService.selectByUserId(user.getId(), false, 20);
    // 已读消息列表
    List<Map<String, Object>> readNotifications = notificationService.selectByUserId(user.getId(), true, 20);
    Map<String, Object> map = new HashMap<>();
    map.put("notRead", notReadNotifications);
    map.put("read", readNotifications);
    return success(map);
  }
}
