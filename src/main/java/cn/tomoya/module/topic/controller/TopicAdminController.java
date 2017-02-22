package cn.tomoya.module.topic.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.Result;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/topic")
public class TopicAdminController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private TopicService topicService;

    /**
     * topic list
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        Page<Topic> page = topicService.page(p == null ? 1 : p, settingService.getPageSize(), null);
        model.addAttribute("page", page);
        return render("/admin/topic/list");
    }

    /**
     * delete topic
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {
        try {
            topicService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

}
