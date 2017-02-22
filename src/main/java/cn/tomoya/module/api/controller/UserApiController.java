package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.Result;
import cn.tomoya.module.collect.service.CollectService;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * user profile interface
     * @param username
     * @return
     * @throws ApiException
     */
    @GetMapping("/{username}")
    public Result profile(@PathVariable String username) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("collectCount", collectService.countByUser(user));
        map.put("topicPage", topicService.findByUser(1, 7, user));
        map.put("replyPage", replyService.findByUser(1, 7, user));
        return Result.success(map);
    }

    /**
     * user topics interface
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/topics")
    public Result topics(@PathVariable String username, Integer p) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        Page page = topicService.findByUser(p == null ? 1 : p, settingService.getPageSize(), user);
        return Result.success(page);
    }

    /**
     * user comment interface
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/replies")
    public Result replies(@PathVariable String username, Integer p) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        Page page = replyService.findByUser(p == null ? 1 : p, settingService.getPageSize(), user);
        return Result.success(page);
    }

    /**
     * user collection interface
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/collects")
    public Result collects(@PathVariable String username, Integer p) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException(localeMessageSourceUtil.getMessage("site.page.user.notExist"));
        Page page = collectService.findByUser(p == null ? 1 : p, settingService.getPageSize(), user);
        return Result.success(page);
    }

}
