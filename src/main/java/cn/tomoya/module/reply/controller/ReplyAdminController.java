package cn.tomoya.module.reply.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/reply")
public class ReplyAdminController extends BaseController {

    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private ReplyService replyService;

    /**
     * 回复列表
     * @param p
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Integer p, Model model) {
        Page<Reply> page = replyService.page(p == null ? 1 : p, siteConfig.getPageSize());
        model.addAttribute("page", page);
        return render("/admin/reply/list");
    }

}
