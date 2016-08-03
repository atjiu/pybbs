package cn.tomoya.common;

import cn.tomoya.interceptor.CommonInterceptor;
import cn.tomoya.template.PyTag;
import cn.tomoya.utils.StrUtil;
import cn.tomoya.utils.ext.plugin.cron.Cron4jPlugin;
import cn.tomoya.utils.ext.plugin.tablebind.AutoTableBindPlugin;
import cn.tomoya.utils.ext.plugin.tablebind.ParamNameStyles;
import cn.tomoya.utils.ext.route.AutoBindRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.FreeMarkerRender;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
public class AppConfig extends JFinalConfig {

    private Routes routes;

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用getProperty(...)获取值
        loadPropertyFile("config.properties");
        me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setFreeMarkerViewExtension("ftl");
        String staticPath = getProperty("static.path");
        me.setBaseUploadPath(StrUtil.isBlank(staticPath) ? "static/upload" : staticPath);
//        me.setMaxPostSize(1024 * 1024 * 2);
        me.setFreeMarkerTemplateUpdateDelay(300);
        me.setError401View("/WEB-INF/page/401.html");
        me.setError404View("/WEB-INF/page/404.html");
        me.setError500View("/WEB-INF/page/500.html");
        FreeMarkerRender.getConfiguration().setSharedVariable("py", new PyTag());
    }

    /**
     * 配置路由
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void configRoute(Routes me) {
        this.routes = me;
        me.add(new AutoBindRoutes());
    }

    /**
     * 配置插件
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(
                getProperty("jdbcUrl"),
                getProperty("user"),
                getProperty("password").trim()
        );
        druidPlugin.setFilters("stat,wall");
        me.add(druidPlugin);
        //增加redis插件
        me.add(new RedisPlugin(
                getProperty("redis.cachename"),
                getProperty("redis.host"),
                getPropertyToInt("redis.port"),
                getPropertyToInt("redis.timeout")
//                getProperty("redis.password"),
//                getPropertyToInt("redis.database")
        ));

        me.add(new Cron4jPlugin().config("cronjob.properties"));

        AutoTableBindPlugin atbp = new AutoTableBindPlugin(
                druidPlugin,
                ParamNameStyles.lowerUnderlineModule("pybbs")
        );
        atbp.addExcludeClasses(BaseModel.class);
        atbp.setShowSql(getPropertyToBoolean("showSql", true));
        me.add(atbp);

    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        me.add(new CommonInterceptor());
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {

    }

    /**
     * 建议使用 JFinal 手册推荐的方式启动项目
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 4000, "/", 5);
    }
}
