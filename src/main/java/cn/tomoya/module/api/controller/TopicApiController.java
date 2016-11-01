package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.Result;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/api/topic")
public class TopicApiController extends BaseController {

    @Autowired
    private TopicService topicService;

    /**
     * 对话题点赞
     *
     * @param topicId
     * @return
     */
    @GetMapping("/up")
    @ResponseBody
    public Result up(Integer topicId) {
        if (topicId != null) {
            User user = getUser();
            topicService.addOneUp(user.getId(), topicId);
            return Result.success();
        }
        return Result.error("点赞失败");
    }

    /**
     * 对话题取消点赞
     *
     * @param topicId
     * @return
     */
    @GetMapping("/unup")
    @ResponseBody
    public Result unup(Integer topicId) {
        if (topicId != null) {
            User user = getUser();
            topicService.reduceOneUp(user.getId(), topicId);
            return Result.success();
        }
        return Result.error("取消点赞失败");
    }

}
