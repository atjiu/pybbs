package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.Result;
import cn.tomoya.module.reply.service.ReplyService;
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
    @GetMapping("/up")
    @ResponseBody
    public Result up(Integer replyId) {
        if (replyId != null) {
            User user = getUser();
            replyService.addOneUp(user.getId(), replyId);
            return Result.success();
        }
        return Result.error("点赞失败");
    }

    /**
     * 对回复取消点赞
     *
     * @param replyId
     * @return
     */
    @GetMapping("/unup")
    @ResponseBody
    public Result unup(Integer replyId) {
        if (replyId != null) {
            User user = getUser();
            replyService.reduceOneUp(user.getId(), replyId);
            return Result.success();
        }
        return Result.error("取消点赞失败");
    }

}
