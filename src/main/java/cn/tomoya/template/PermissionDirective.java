package cn.tomoya.template;

import cn.tomoya.module.system.Permission;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class PermissionDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody)
            throws TemplateException, IOException {

        boolean b = false;
        if(map.containsKey("name") && map.get("name") != null && map.get("id") != null) {
            Map<String, String> permissions = Permission.me.findPermissions(Integer.parseInt(map.get("id").toString()));
            b = permissions.containsKey(map.get("name").toString());
        }

        if(b && templateDirectiveBody != null) {
            templateDirectiveBody.render(environment.getOut());
        }
    }

}
