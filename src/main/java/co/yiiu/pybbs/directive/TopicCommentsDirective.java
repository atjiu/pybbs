package co.yiiu.pybbs.directive;

import co.yiiu.pybbs.service.ICommentService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class TopicCommentsDirective implements TemplateDirectiveModel {

    @Resource
    private ICommentService commentService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody
            templateDirectiveBody) throws TemplateException, IOException {
        Integer topicId = Integer.parseInt(map.get("topicId").toString());
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        environment.setVariable("comments", builder.build().wrap(commentService.selectByTopicId(topicId)));
        templateDirectiveBody.render(environment.getOut());
    }
}
