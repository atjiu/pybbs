package co.yiiu.web.front;

import co.yiiu.core.base.BaseController;
import co.yiiu.module.collect.model.Collect;
import co.yiiu.module.collect.service.CollectService;
import co.yiiu.module.notification.model.NotificationEnum;
import co.yiiu.module.notification.service.NotificationService;
import co.yiiu.module.topic.model.Topic;
import co.yiiu.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
  @Autowired
  private NotificationService notificationService;

  @GetMapping("/{topicId}/add")
  public String add(@PathVariable Integer topicId, HttpServletResponse response) throws Exception {
    Topic topic = topicService.findById(topicId);

    if (topic == null) throw new Exception("话题不存在");

    Collect collect = collectService.findByUserAndTopic(getUser(), topic);
    if (collect != null) throw new Exception("你已经收藏了这个话题");

    collect = new Collect();
    collect.setInTime(new Date());
    collect.setTopic(topic);
    collect.setUser(getUser());
    collectService.save(collect);

    //发出通知
    notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.COLLECT.name(), topic, "");
    return redirect(response, "/topic/" + topic.getId());
  }

  /**
   * 删除收藏
   *
   * @param topicId
   * @param response
   * @return
   */
  @GetMapping("/{topicId}/delete")
  public String delete(@PathVariable Integer topicId, HttpServletResponse response) throws Exception {
    Topic topic = topicService.findById(topicId);
    Collect collect = collectService.findByUserAndTopic(getUser(), topic);

    if (collect == null) throw new Exception("你还没收藏这个话题");

    collectService.deleteById(collect.getId());
    return redirect(response, "/topic/" + topic.getId());
  }
}
