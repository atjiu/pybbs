package cn.tomoya.module.setting.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.module.setting.entity.Setting;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin/setting")
public class SettingController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    @GetMapping("/detail")
    public String detail(Model model) {
        model.addAttribute("themeSetting", settingService.findByName("theme"));
        model.addAttribute("pageSizeSetting", settingService.findByName("page_size"));
        model.addAttribute("baseUrlSetting", settingService.findByName("base_url"));
        model.addAttribute("editorSetting", settingService.findByName("editor"));
        model.addAttribute("uploadPathSetting", settingService.findByName("upload_path"));
        model.addAttribute("avatarPathSetting", settingService.findByName("avatar_path"));
        model.addAttribute("searchSetting", settingService.findByName("search"));

        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.setting.detail"));
        return render("/admin/setting/detail");
    }

    @PostMapping("/update")
    public String update(String theme, String pageSize, String baseUrl, String editor, String uploadPath, String avatarPath,
                         String search,
                         HttpServletResponse response) {
        Setting themeSetting = settingService.findByName("theme");
        themeSetting.setValue(theme);
        settingService.save(themeSetting);

        Setting pageSizeSetting = settingService.findByName("page_size");
        pageSizeSetting.setValue(pageSize);
        settingService.save(pageSizeSetting);

        Setting baseUrlSetting = settingService.findByName("base_url");
        baseUrlSetting.setValue(baseUrl);
        settingService.save(baseUrlSetting);

        Setting editorSetting = settingService.findByName("editor");
        editorSetting.setValue(editor);
        settingService.save(editorSetting);

        Setting uploadPathSetting = settingService.findByName("upload_path");
        uploadPathSetting.setValue(uploadPath);
        settingService.save(uploadPathSetting);

        Setting avatarPathSetting = settingService.findByName("avatar_path");
        avatarPathSetting.setValue(avatarPath);
        settingService.save(avatarPathSetting);

        Setting searchSetting = settingService.findByName("search");
        searchSetting.setValue(search);
        settingService.save(searchSetting);

        settingService.clearCache();

        return redirect(response, "/admin/setting/detail");
    }
}
