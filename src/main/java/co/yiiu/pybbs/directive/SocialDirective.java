package co.yiiu.pybbs.directive;

import co.yiiu.pybbs.plugin.SocialPlugin;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 社交化登录相关的自定义标签
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2020/6/25 0:07
 * @since 1.0.0
 */
@Component
public class SocialDirective implements TemplateDirectiveModel {

    @Resource
    private SocialPlugin socialPlugin;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody
            templateDirectiveBody) throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        environment.setVariable("socialList", builder.build().wrap(socialPlugin.getAllAvailableSocialPlatform()));
        templateDirectiveBody.render(environment.getOut());
    }
}
