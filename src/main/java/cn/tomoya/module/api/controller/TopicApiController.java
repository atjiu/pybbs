package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.Result;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@RestController
@RequestMapping("/api/topic")
public class TopicApiController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private CollectService collectService;

    /**
     * topic detail interface
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(String token, @PathVariable Integer id) throws ApiException {
        Topic topic = topicService.findById(id);
        if(topic == null) throw new ApiException("话题不存在");
        Map<String, Object> map = new HashMap<>();
        //浏览量+1
        topic.setView(topic.getView() + 1);
        topicService.save(topic);//更新话题数据
        List<Reply> replies = replyService.findByTopic(topic);
        map.put("topic", topic);
        map.put("replies", replies);
        map.put("author", topic.getUser());
        map.put("collectCount", collectService.countByTopic(topic));
        return Result.success(map);
    }
}
