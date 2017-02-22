package cn.tomoya.module.api.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.exception.ApiException;
import cn.tomoya.exception.Result;
import cn.tomoya.module.section.service.SectionService;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.topic.entity.Topic;
import cn.tomoya.module.topic.service.TopicService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@RestController
@RequestMapping("/api")
public class IndexApiController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * topic list interface
     * @param tab
     * @param p
     * @return
     * @throws ApiException
     */
    @GetMapping("/index")
    public Result index(String tab, Integer p) throws ApiException {
        if(!StringUtils.isEmpty(tab) && sectionService.findByName(tab) != null) throw new ApiException(localeMessageSourceUtil.getMessage("site.prompt.text.tabNotExist"));
        if (StringUtils.isEmpty(tab)) tab = localeMessageSourceUtil.getMessage("site.tab.all");
        Page<Topic> page = topicService.page(p == null ? 1 : p, settingService.getPageSize(), tab);
        return Result.success(page);
    }
}
