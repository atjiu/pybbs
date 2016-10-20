package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 查询当前用户未读的消息数量
     * @return
     */
    @RequestMapping(value = "/notRead", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String notRead() {
        User user = getUser();
        if(user == null) {
            return JsonUtil.error("用户未登录");
        } else {
            return JsonUtil.success(notificationService.countByTargetUserAndIsRead(user, false));
        }
    }
}
