package com.jfinalbbs.module.section;

import com.jfinalbbs.common.BaseController;
import com.jfinalbbs.utils.ext.route.ControllerBind;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
@ControllerBind(controllerKey = "/api/section")
public class SectionClientController extends BaseController {

    public void index() {
        success(Section.me.findShow());
    }
}
