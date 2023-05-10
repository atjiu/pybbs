package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.config.service.TelegramBotService;
import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://atjiu.github.io
 */
@Controller
@RequestMapping("/admin/system")
public class SystemConfigAdminController extends BaseAdminController {

    private Logger log = LoggerFactory.getLogger(SystemConfigAdminController.class);

    @Resource
    private ISystemConfigService systemConfigService;
    @Resource
    private TelegramBotService telegramBotService;

    @RequiresPermissions("system:edit")
    @GetMapping("/edit")
    public String edit(Model model) {
        model.addAttribute("systems", systemConfigService.selectAll());
        return "admin/system/edit";
    }

    @RequiresPermissions("system:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@RequestBody List<Map<String, String>> list) {
        Map<String, String> flattenedMap = new HashMap<>();
        list.forEach(map -> flattenedMap.put(map.get("name"), map.get("value")));
        new Thread(() -> telegramBotService.init().setWebHook(flattenedMap)).start();

        systemConfigService.update(list);
        return success();
    }
}
