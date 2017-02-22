package cn.tomoya.module.notification.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.ErrorCode;
import cn.tomoya.exception.Result;
import cn.tomoya.module.notification.service.NotificationService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/notification")
public class NotificationController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * notification list
     *
     * @param p
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Integer p, Model model) {
        model.addAttribute("page", notificationService.findByTargetUserAndIsRead(p == null ? 1 : p, settingService.getPageSize(), getUser(), null));
        //set unread notification to read
        notificationService.updateByIsRead(getUser());
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.notification.list"));
        return render("/notification/list");
    }

    /**
     * query the number of notification that the current user has not read
     *
     * @return
     */
    @GetMapping("/notRead")
    @ResponseBody
    public Result notRead() throws ApiException {
        User user = getUser();
        if (user == null) throw new ApiException(ErrorCode.notLogin, localeMessageSourceUtil.getMessage("site.prompt.text.notLogin"));
        return Result.success(notificationService.countByTargetUserAndIsRead(user, false));
    }
}
