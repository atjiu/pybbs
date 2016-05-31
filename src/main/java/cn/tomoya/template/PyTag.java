package cn.tomoya.template;

import freemarker.template.SimpleHash;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://bbs.tomoya.cn
 */
public class PyTag extends SimpleHash {

    public PyTag() {
        put("hasPermission", new PermissionDirective());
        put("scores", new ScoresDirective());
    }
}
