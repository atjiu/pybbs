package cn.tomoya.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by ZXF-PC1 on 2015/7/23.
 */
//@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private CustomTags customTags;

    @PostConstruct
    public void setSharedVariable(){
        configuration.setSharedVariable("custom",customTags);
    }
}
