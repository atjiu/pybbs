package cn.tomoya.module.reply.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.reply.entity.Reply;
import cn.tomoya.module.reply.service.ReplyService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private SettingService settingService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * comment list
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        Page<Reply> page = replyService.page(p == null ? 1 : p, settingService.getPageSize());
        model.addAttribute("page", page);
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.reply.list"));
        return render("/admin/reply/list");
    }

}
