package com.jfinalbbs.common;

import com.jfinal.config.Constants;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinalbbs.collect.Collect;
import com.jfinalbbs.collect.CollectClientController;
import com.jfinalbbs.collect.CollectController;
import com.jfinalbbs.handler.HtmlHandler;
import com.jfinalbbs.index.IndexAdminController;
import com.jfinalbbs.index.IndexClientController;
import com.jfinalbbs.index.IndexController;
import com.jfinalbbs.interceptor.AdminUserInterceptor;
import com.jfinalbbs.interceptor.CommonInterceptor;
import com.jfinalbbs.label.*;
import com.jfinalbbs.link.Link;
import com.jfinalbbs.link.LinkAdminController;
import com.jfinalbbs.message.Message;
import com.jfinalbbs.message.MsgContact;
import com.jfinalbbs.message.MsgMessageController;
import com.jfinalbbs.mission.Mission;
import com.jfinalbbs.mission.MissionAdminController;
import com.jfinalbbs.mission.MissionClientController;
import com.jfinalbbs.mission.MissionController;
import com.jfinalbbs.notification.Notification;
import com.jfinalbbs.notification.NotificationClientController;
import com.jfinalbbs.notification.NotificationController;
import com.jfinalbbs.oauth.OauthController;
import com.jfinalbbs.reply.Reply;
import com.jfinalbbs.reply.ReplyAdminController;
import com.jfinalbbs.reply.ReplyClientController;
import com.jfinalbbs.reply.ReplyController;
import com.jfinalbbs.section.Section;
import com.jfinalbbs.section.SectionAdminController;
import com.jfinalbbs.section.SectionClientController;
import com.jfinalbbs.system.SysConfig;
import com.jfinalbbs.system.SysConfigAdminController;
import com.jfinalbbs.topic.Topic;
import com.jfinalbbs.topic.TopicAdminController;
import com.jfinalbbs.topic.TopicClientController;
import com.jfinalbbs.topic.TopicController;
import com.jfinalbbs.user.*;
import com.jfinalbbs.valicode.ValiCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class JFinalbbsConfig extends JFinalConfig {

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
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        //添加前台路由
        frontRoute(me);
        //添加后台路由
        adminRoute(me);
        //添加客户端路由
        clientRoute(me);
    }

    //前台路由配置
    public void frontRoute(Routes me) {
        me.add("/", IndexController.class, "page");    // 第三个参数为该Controller的视图存放路径
        me.add("/topic", TopicController.class, "page");
        me.add("/user", UserController.class, "page");
        me.add("/mission", MissionController.class, "page");
        me.add("/reply", ReplyController.class, "page");
        me.add("/collect", CollectController.class, "page");
        me.add("/notification", NotificationController.class, "page");
        me.add("/label", LabelController.class, "page");
        me.add("/oauth", OauthController.class);
        me.add("/message", MsgMessageController.class, "page");
    }

    //后台路由配置
    public void adminRoute(Routes me) {
        me.add("/admin", IndexAdminController.class, "page/admin");
        me.add("/admin/topic", TopicAdminController.class, "page/admin/topic");
        me.add("/admin/reply", ReplyAdminController.class, "page/admin/reply");
        me.add("/admin/user", UserAdminController.class, "page/admin/user");
        me.add("/admin/section", SectionAdminController.class, "page/admin/section");
        me.add("/admin/link", LinkAdminController.class, "page/admin/link");
        me.add("/admin/mission", MissionAdminController.class, "page/admin/mission");
        me.add("/admin/label", LabelAdminController.class, "page/admin/label");
        me.add("/admin/sysconfig", SysConfigAdminController.class, "page/admin/sysconfig");
    }

    public void clientRoute(Routes me) {
        me.add("/api/index", IndexClientController.class);
        me.add("/api/topic", TopicClientController.class);
        me.add("/api/reply", ReplyClientController.class);
        me.add("/api/user", UserClientController.class);
        me.add("/api/notification", NotificationClientController.class);
        me.add("/api/section", SectionClientController.class);
        me.add("/api/collect", CollectClientController.class);
        me.add("/api/mission", MissionClientController.class);
        me.add("/api/label", LabelClientController.class);
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
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setShowSql(getPropertyToBoolean("showSql", false));
        arp.addMapping("jfbbs_topic", Topic.class);    // 映射blog 表到 Blog模型
        arp.addMapping("jfbbs_reply", Reply.class);
        arp.addMapping("jfbbs_user", User.class);
        arp.addMapping("jfbbs_mission", Mission.class);
        arp.addMapping("jfbbs_collect", Collect.class);
        arp.addMapping("jfbbs_notification", Notification.class);
        arp.addMapping("jfbbs_admin_user", AdminUser.class);
        arp.addMapping("jfbbs_section", Section.class);
        arp.addMapping("jfbbs_link", Link.class);
        arp.addMapping("jfbbs_valicode", ValiCode.class);
        arp.addMapping("jfbbs_label", Label.class);
        arp.addMapping("jfbbs_label_topic_id", LabelTopicId.class);
        arp.addMapping("jfbbs_sys_config", SysConfig.class);
        arp.addMapping("jfbbs_message", Message.class);
        arp.addMapping("jfbbs_msg_contact", MsgContact.class);
        me.add(arp);
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        me.add(new SessionInViewInterceptor());
        me.add(new CommonInterceptor());
        me.add(new AdminUserInterceptor());
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
        //配置druid的监听，可以在浏览器里输入http://localhost:8080/druid 查看druid监听的数据
//        me.add(new DruidStatViewHandler("/druid"));
        me.add(new HtmlHandler());
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
        JFinal.start("src/main/webapp", 8080, "/", 5);
    }
}
