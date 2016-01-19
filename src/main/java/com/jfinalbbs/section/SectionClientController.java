package com.jfinalbbs.section;

import com.jfinalbbs.common.BaseController;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class SectionClientController extends BaseController {

    public void index() {
        success(Section.me.findShow());
    }
}
