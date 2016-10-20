package cn.tomoya.template;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class TestDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody)
            throws TemplateException, IOException {
        List list = new ArrayList();
        for(int i = 0; i < 10; i++) {
            list.add("hello " + i);
        }
        environment.setVariable("testList", new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25).build().wrap(list));
        if(templateDirectiveBody != null) {
            templateDirectiveBody.render(environment.getOut());
        }
    }

}