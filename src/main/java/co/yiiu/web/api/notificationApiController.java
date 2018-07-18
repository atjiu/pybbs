package co.yiiu.web.api;

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
 * Created by tomoya at 2018/4/12
 */
@RestController
@RequestMapping("/api/notification")
public class notificationApiController extends BaseController {

  @Autowired
  private NotificationService notificationService;

  /**
   * 查询当前用户未读的消息数量
   *
   * @return
   */
  @GetMapping("/notRead")
  public Result notRead() throws ApiException {
    User user = getApiUser();
    return Result.success(notificationService.countByTargetUserAndIsRead(user, false));
  }
}
