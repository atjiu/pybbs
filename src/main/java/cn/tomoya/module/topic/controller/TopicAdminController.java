package cn.tomoya.module.topic.controller;

import cn.tomoya.config.base.BaseController;
import cn.tomoya.config.yml.SiteConfig;
import cn.tomoya.exception.Result;
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
    private SiteConfig siteConfig;
    @Autowired
    private TopicService topicService;

    /**
     * 话题列表
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        Page<Topic> page = topicService.page(p == null ? 1 : p, siteConfig.getPageSize(), null);
        model.addAttribute("page", page);
        return render("/admin/topic/list");
    }

    /**
     * 删除话题
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
