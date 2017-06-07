package cn.tomoya.module.index.controller;

import cn.tomoya.config.base.BaseController;
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

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return render("/admin/index");
    }

    /**
     * 索引首页
     *
     * @return
     */
    @GetMapping("/indexed")
    public String indexed(String s, Model model) {
        model.addAttribute("s", s);
        return render("/admin/indexed/index");
    }

    /**
     * 索引全部话题
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
     * 删除全部索引
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
