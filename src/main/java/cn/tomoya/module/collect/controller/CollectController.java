package cn.tomoya.module.collect.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.collect.entity.Collect;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.notification.entity.Notification;
import cn.tomoya.module.notification.entity.NotificationEnum;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
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

    @RequestMapping("/{topicId}/add")
    public String add(@PathVariable Integer topicId, HttpServletResponse response) {
        Topic topic = topicService.findById(topicId);
        if(topic == null) {
            renderText(response, "话题不存在");
            return null;
        } else {
            Collect collect = new Collect();
            collect.setInTime(new Date());
            collect.setTopic(topic);
            collect.setUser(getUser());
            collectService.save(collect);

            //发出通知
            notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.COLLECT.name(), topic, "");
            return redirect(response, "/topic/" + topic.getId());
        }
    }

    /**
     * 删除收藏
     * @param topicId
     * @param response
     * @return
     */
    @RequestMapping("/{topicId}/delete")
    public String delete(@PathVariable Integer topicId, HttpServletResponse response) {
        Topic topic = topicService.findById(topicId);
        Collect collect = collectService.findByUserAndTopic(getUser(), topic);
        if(collect == null) {
            renderText(response, "你还没收藏这个话题");
            return null;
        } else {
            collectService.deleteById(collect.getId());
            return redirect(response, "/topic/" + topic.getId());
        }
    }
}
