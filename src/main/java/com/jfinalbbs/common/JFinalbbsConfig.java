package com.jfinalbbs.common;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.jfinal.config.Constants;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinalbbs.handler.HtmlHandler;
import com.jfinalbbs.interceptor.CommonInterceptor;
import com.jfinalbbs.utils.ext.plugin.shiro.ShiroInterceptor;
import com.jfinalbbs.utils.ext.plugin.shiro.ShiroPlugin;
import com.jfinalbbs.utils.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinalbbs.utils.ext.plugin.tablebind.ParamNameStyles;
import com.jfinalbbs.utils.ext.route.AutoBindRoutes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class JFinalbbsConfig extends JFinalConfig {

    private Routes routes;

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用getProperty(...)获取值
        loadPropertyFile("config.properties");
        me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setFreeMarkerViewExtension("ftl");
        me.setBaseUploadPath(com.jfinalbbs.common.Constants.UPLOAD_DIR);
        me.setMaxPostSize(2048000);
        me.setFreeMarkerTemplateUpdateDelay(0);

        me.setErrorView(401, "/adminlogin");
        me.setErrorView(403, "/adminlogin");
        me.setError401View("/page/front/401.html");
        me.setError403View("/page/front/403.html");
        me.setError500View("/page/front/500.html");
        FreeMarkerRender.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        this.routes = me;
        me.add(new AutoBindRoutes());
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        druidPlugin.setFilters("stat,wall");
        me.add(druidPlugin);
        me.add(new EhCachePlugin());

        AutoTableBindPlugin atbp = new AutoTableBindPlugin(druidPlugin, ParamNameStyles.lowerUnderlineModule("jfbbs"));
        atbp.addExcludeClasses(BaseModel.class);
        atbp.setShowSql(true);
        me.add(atbp);

        ShiroPlugin shiroPlugin = new ShiroPlugin(routes);
        me.add(shiroPlugin);
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        me.add(new SessionInViewInterceptor());
        me.add(new ShiroInterceptor());
        me.add(new CommonInterceptor());
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
        //配置druid的监听，可以在浏览器里输入http://localhost:8080/druid 查看druid监听的数据
//        me.add(new DruidStatViewHandler("/druid"));
        me.add(new HtmlHandler());
        me.add(new ContextPathHandler("baseUrl"));
    }

    /**
     * 启动完毕所做的处理
     */
    public void afterJFinalStart() {
        try {
            String qq_properties = "qqconnectconfig.properties";
            Properties qq_prop = PropKit.use(qq_properties).getProperties();
            qq_prop.setProperty("app_ID", com.jfinalbbs.common.Constants.getValue("qq_appId"));
            qq_prop.setProperty("app_KEY", com.jfinalbbs.common.Constants.getValue("qq_appKey"));
            qq_prop.setProperty("redirect_URI", com.jfinalbbs.common.Constants.getValue("qq_redirect_URI"));
            qq_prop.store(new FileOutputStream(PathKit.getRootClassPath() + "/" + qq_properties), "read db config write to prop file");

            String sina_properties = "weiboconfig.properties";
            Properties sina_prop = PropKit.use(sina_properties).getProperties();
            sina_prop.setProperty("client_ID", com.jfinalbbs.common.Constants.getValue("sina_clientId"));
            sina_prop.setProperty("client_SERCRET", com.jfinalbbs.common.Constants.getValue("sina_clientSercret"));
            sina_prop.setProperty("redirect_URI", com.jfinalbbs.common.Constants.getValue("sina_redirect_URI"));
            sina_prop.store(new FileOutputStream(PathKit.getRootClassPath() + "/" + sina_properties), "read db config write to prop file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 建议使用 JFinal 手册推荐的方式启动项目
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        System.out.println(HashKit.md5("123456"));
        JFinal.start("src/main/webapp", 8080, "/", 5);
    }
}
