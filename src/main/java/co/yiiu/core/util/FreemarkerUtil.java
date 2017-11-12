package co.yiiu.core.util;

import co.yiiu.core.util.freemarker.StringTemplateLoader;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
@Component
public class FreemarkerUtil {


    private final Log logger = LogFactory.getLog(FreemarkerUtil.class);


    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    private static final Map<String, Template> cachedTemplate = Maps.newConcurrentMap();

    public String format(String template, Map<String, Object> objectMap) {
        StringWriter writer = new StringWriter();
        try {
            getTemplateConfiguration(template).process(objectMap, writer);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
            logger.error("render template error", e);
        }
        return writer.toString();
    }

    private Template getTemplateConfiguration(String template) {

        if (cachedTemplate.containsKey(template)) {
            return cachedTemplate.get(template);
        }
        Configuration configuration = null;
        try {
            configuration = freeMarkerConfigurer.createConfiguration();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            logger.error("get system configuration error", e);
        }
        configuration.setTemplateLoader(new StringTemplateLoader(template));
        configuration.setDefaultEncoding("UTF-8");


        try {
            Template stringTemplate = configuration.getTemplate("");
            cachedTemplate.put(template, stringTemplate);
            return stringTemplate;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get template error", e);
        }
        return null;
    }

}
