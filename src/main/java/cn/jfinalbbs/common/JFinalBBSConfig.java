package cn.jfinalbbs.common;

import cn.jfinalbbs.collect.Collect;
import cn.jfinalbbs.collect.CollectClientController;
import cn.jfinalbbs.collect.CollectController;
import cn.jfinalbbs.handler.HtmlHandler;
import cn.jfinalbbs.index.IndexAdminController;
import cn.jfinalbbs.index.IndexClientController;
import cn.jfinalbbs.index.IndexController;
import cn.jfinalbbs.interceptor.CommonInterceptor;
import cn.jfinalbbs.label.Label;
import cn.jfinalbbs.label.LabelAdminController;
import cn.jfinalbbs.label.LabelController;
import cn.jfinalbbs.label.LabelTopicId;
import cn.jfinalbbs.link.Link;
import cn.jfinalbbs.link.LinkAdminController;
import cn.jfinalbbs.mission.Mission;
import cn.jfinalbbs.mission.MissionAdminController;
import cn.jfinalbbs.mission.MissionClientController;
import cn.jfinalbbs.mission.MissionController;
import cn.jfinalbbs.notification.Notification;
import cn.jfinalbbs.notification.NotificationClientController;
import cn.jfinalbbs.notification.NotificationController;
import cn.jfinalbbs.reply.Reply;
import cn.jfinalbbs.reply.ReplyAdminController;
import cn.jfinalbbs.reply.ReplyClientController;
import cn.jfinalbbs.reply.ReplyController;
import cn.jfinalbbs.section.Section;
import cn.jfinalbbs.section.SectionAdminController;
import cn.jfinalbbs.section.SectionClientController;
import cn.jfinalbbs.topic.Topic;
import cn.jfinalbbs.topic.TopicAdminController;
import cn.jfinalbbs.topic.TopicClientController;
import cn.jfinalbbs.topic.TopicController;
import cn.jfinalbbs.user.*;
import cn.jfinalbbs.valicode.ValiCode;
import com.jfinal.config.Constants;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * API引导式配置
 */
public class JFinalbbsConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setBaseUploadPath(cn.jfinalbbs.common.Constants.UPLOAD_DIR);
		me.setMaxPostSize(2048000);
    }
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "page");	// 第三个参数为该Controller的视图存放路径
		me.add("/topic", TopicController.class, "page");
		me.add("/user", UserController.class, "page");
		me.add("/mission", MissionController.class, "page");
		me.add("/reply", ReplyController.class, "page");
		me.add("/collect", CollectController.class, "page");
		me.add("/notification", NotificationController.class, "page");
		me.add("/label", LabelController.class, "page");
        //添加后台路由
		adminRoute(me);
        //添加客户端路由
        clientRoute(me);
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
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
//		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        druidPlugin.setFilters("stat,wall");

//		me.add(c3p0Plugin);
		me.add(druidPlugin);

        me.add(new EhCachePlugin());
		
		// 配置ActiveRecord插件
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setShowSql(getPropertyToBoolean("showSql", false));
		me.add(arp);
		arp.addMapping("topic", Topic.class);	// 映射blog 表到 Blog模型
		arp.addMapping("reply", Reply.class);
		arp.addMapping("user", User.class);
		arp.addMapping("mission", Mission.class);
		arp.addMapping("collect", Collect.class);
		arp.addMapping("notification", Notification.class);
		arp.addMapping("admin_user", AdminUser.class);
		arp.addMapping("section", Section.class);
		arp.addMapping("link", Link.class);
		arp.addMapping("valicode", ValiCode.class);
		arp.addMapping("label", Label.class);
		arp.addMapping("label_topic_id", LabelTopicId.class);
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
        //配置druid的监听，可以在浏览器里输入http://localhost:8080/druid 查看druid监听的数据
        me.add(new DruidStatViewHandler("/druid"));
        me.add(new HtmlHandler());
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
