package cn.jfinalbbs.common;

import cn.jfinalbbs.collect.Collect;
import cn.jfinalbbs.collect.CollectController;
import cn.jfinalbbs.handler.HtmlHandler;
import cn.jfinalbbs.index.IndexAdminController;
import cn.jfinalbbs.index.IndexController;
import cn.jfinalbbs.interceptor.CommonInterceptor;
import cn.jfinalbbs.link.Link;
import cn.jfinalbbs.link.LinkAdminController;
import cn.jfinalbbs.mission.Mission;
import cn.jfinalbbs.mission.MissionController;
import cn.jfinalbbs.notification.Notification;
import cn.jfinalbbs.notification.NotificationController;
import cn.jfinalbbs.reply.ReplyAdminController;
import cn.jfinalbbs.reply.Reply;
import cn.jfinalbbs.reply.ReplyController;
import cn.jfinalbbs.section.Section;
import cn.jfinalbbs.section.SectionAdminController;
import cn.jfinalbbs.system.Code;
import cn.jfinalbbs.topic.TopicAdminController;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.topic.TopicController;
import cn.jfinalbbs.user.UserAdminController;
import cn.jfinalbbs.user.AdminUser;
import cn.jfinalbbs.user.User;
import cn.jfinalbbs.user.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * API引导式配置
 */
public class JFinalBBSConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
    }
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "ftl/front");	// 第三个参数为该Controller的视图存放路径
		me.add("/topic", TopicController.class, "ftl/front/topic");
		me.add("/user", UserController.class, "ftl/front/user");
		me.add("/mission", MissionController.class, "ftl/front/mission");
		me.add("/reply", ReplyController.class, "ftl/front/topic");
		me.add("/collect", CollectController.class, "ftl/front/collect");
		me.add("/notification", NotificationController.class, "ftl/front/notification");
        //添加后台路由
        adminRoute(me);
	}

	//后台路由配置
    public void adminRoute(Routes me) {
        me.add("/admin", IndexAdminController.class, "ftl/admin");
        me.add("/admin/topic", TopicAdminController.class, "ftl/admin/topic");
        me.add("/admin/reply", ReplyAdminController.class, "ftl/admin/reply");
        me.add("/admin/user", UserAdminController.class, "ftl/admin/user");
        me.add("/admin/section", SectionAdminController.class, "ftl/admin/section");
        me.add("/admin/link", LinkAdminController.class, "ftl/admin/link");
    }

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);

        me.add(new EhCachePlugin());
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setShowSql(getPropertyToBoolean("showSql", false));
		me.add(arp);
		arp.addMapping("topic", Topic.class);	// 映射blog 表到 Blog模型
		arp.addMapping("reply", Reply.class);
		arp.addMapping("user", User.class);
		arp.addMapping("mission", Mission.class);
		arp.addMapping("collect", Collect.class);
		arp.addMapping("notification", Notification.class);
		arp.addMapping("admin_user", AdminUser.class);
		arp.addMapping("code", Code.class);
		arp.addMapping("section", Section.class);
		arp.addMapping("link", Link.class);

	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
        me.add(new SessionInViewInterceptor());
        me.add(new CommonInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
        me.add(new HtmlHandler());
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);
	}
}
