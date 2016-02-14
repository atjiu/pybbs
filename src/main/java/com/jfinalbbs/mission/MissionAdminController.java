package com.jfinalbbs.mission;

import com.jfinalbbs.common.BaseController;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class MissionAdminController extends BaseController {

    public void index() {
        setAttr("page", Mission.me.paginate(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }
}
