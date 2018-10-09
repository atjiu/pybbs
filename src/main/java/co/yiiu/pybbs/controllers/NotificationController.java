package co.yiiu.pybbs.controllers;

import co.yiiu.pybbs.conf.properties.SiteConfig;
import co.yiiu.pybbs.models.Notification;
import co.yiiu.pybbs.models.Topic;
import co.yiiu.pybbs.models.User;
import co.yiiu.pybbs.services.NotificationService;
import co.yiiu.pybbs.services.TopicService;
import co.yiiu.pybbs.services.UserService;
import co.yiiu.pybbs.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya at 2018/9/14
 */
@RestController
@RequestMapping("/notification")
public class NotificationController extends BaseController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private UserService userService;

  @GetMapping("/list")
  public Result list(@RequestParam(defaultValue = "1") Integer pageNo) {
    User user = getUser();
    Page<Notification> page = notificationService.findByUserId(user.getId(), pageNo, siteConfig.getPageSize());
    page.getContent().forEach(notification -> {
      Topic topic = topicService.findById(notification.getTopicId());
      User _user = userService.findById(notification.getUserId());
      notification.setTopic(topic);
      notification.setUser(_user);
    });
    return Result.success(page);
  }

  @GetMapping("/notRead")
  public Result notRead() {
    User user = getUser();
    long count = notificationService.countNotRead(user.getId());
    return Result.success(count);
  }
}
