package cn.tomoya.module.index.controller;

import cn.tomoya.common.BaseController;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController extends BaseController {

    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    /**
     * administrator home
     *
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.index"));
        return render("/admin/index");
    }

    /**
     * indexed page
     *
     * @return
     */
    @GetMapping("/indexed")
    public String indexed(String s, Model model) {
        model.addAttribute("s", s);
        model.addAttribute("pageTitle", localeMessageSourceUtil.getMessage("site.page.admin.indexed.index"));
        return render("/admin/indexed/index");
    }

    /**
     * indexed topics
     *
     * @param response
     * @return
     */
    @GetMapping("/indexed/indexAll")
    public String indexedAll(HttpServletResponse response) {
        //TODO
        return redirect(response, "/admin/indexed?s=add");
    }

    /**
     * delete indexed
     *
     * @param response
     * @return
     */
    @GetMapping("/indexed/deleteAll")
    public String deleteAllIndexed(HttpServletResponse response) {
        //TODO
        return redirect(response, "/admin/indexed?s=del");
    }

}
