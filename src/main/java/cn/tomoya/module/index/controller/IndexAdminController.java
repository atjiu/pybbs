package cn.tomoya.module.index.controller;

import cn.tomoya.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/index")
    public String index() {
        return render("/admin/index");
    }

}
