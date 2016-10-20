package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.reply.service.ReplyService;
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
@RequestMapping("/api/reply")
public class ReplyApiController extends BaseController {

    @Autowired
    private ReplyService replyService;

    /**
     * 对回复点赞
     *
     * @param replyId
     * @return
     */
    @RequestMapping(value = "/up", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String up(Integer replyId) {
        if (replyId != null) {
            User user = getUser();
            replyService.addOneUp(user.getId(), replyId);
            return JsonUtil.success();
        }
        return JsonUtil.error("点赞失败");
    }

    /**
     * 对回复取消点赞
     *
     * @param replyId
     * @return
     */
    @RequestMapping(value = "/unup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String unup(Integer replyId) {
        if (replyId != null) {
            User user = getUser();
            replyService.reduceOneUp(user.getId(), replyId);
            return JsonUtil.success();
        }
        return JsonUtil.error("取消点赞失败");
    }

}
