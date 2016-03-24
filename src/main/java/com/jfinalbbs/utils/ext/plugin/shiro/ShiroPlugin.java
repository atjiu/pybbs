/**
 * Copyright (c) 2011-2013, dafei 李飞 (myaniu AT gmail DOT com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jfinalbbs.utils.ext.plugin.shiro;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;

import com.jfinal.config.Routes;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.IPlugin;

/**
 * Shiro插件，启动时加载所有Shiro访问控制注解。
 * @author dafei
 *
 */
@SuppressWarnings("unchecked")
public class ShiroPlugin implements IPlugin {

	/**
	 * 登录成功时所用的页面。
	 */
	private  String successUrl = "/admin/index";

	/**
	 * 登录成功时所用的页面。
	 */
	private  String loginUrl = "/adminlogin";


	/**
	 * 登录成功时所用的页面。
	 */
	private  String unauthorizedUrl ="/401";


	private  final String SLASH = "/";

	/**
	 * Shiro的几种访问控制注解
	 */
	private static final Class<? extends Annotation>[] AUTHZ_ANNOTATION_CLASSES = new Class[] {
			RequiresPermissions.class, RequiresRoles.class, RequiresUser.class,
			RequiresGuest.class, RequiresAuthentication.class };

	/**
	 * 路由设定
	 */
	private final Routes routes;

	/**
	 * 构造函数
	 * @param routes 路由设定
	 */
	public ShiroPlugin(Routes routes){
		this.routes = routes;
	}

	/**
	 * 停止插件
	 */
	public boolean stop() {
		return true;
	}

	/**
	 * 启动插件
	 */
	public boolean start() {
		Set<String> excludedMethodName = buildExcludedMethodName();
		ConcurrentMap<String, AuthzHandler> authzMaps = new ConcurrentHashMap<String, AuthzHandler>();
		//逐个访问所有注册的Controller，解析Controller及action上的所有Shiro注解。
		//并依据这些注解，actionKey提前构建好权限检查处理器。
		for (Entry<String, Class<? extends Controller>> entry : routes
				.getEntrySet()) {
			Class<? extends Controller> controllerClass = entry.getValue();

			String controllerKey = entry.getKey();

			// 获取Controller的所有Shiro注解。
			List<Annotation> controllerAnnotations = getAuthzAnnotations(controllerClass);
			// 逐个遍历方法。
			Method[] methods = controllerClass.getMethods();
			for (Method method : methods) {
				//排除掉Controller基类的所有方法，并且只关注没有参数的Action方法。
				if (!excludedMethodName.contains(method.getName())
						&& method.getParameterTypes().length == 0) {
					//若该方法上存在ClearShiro注解，则对该action不进行访问控制检查。
					if(isClearShiroAnnotationPresent(method)){
						continue;
					}
					//获取方法的所有Shiro注解。
					List<Annotation> methodAnnotations = getAuthzAnnotations(method);
					//依据Controller的注解和方法的注解来生成访问控制处理器。
					AuthzHandler authzHandler = createAuthzHandler(
							controllerAnnotations, methodAnnotations);
					//生成访问控制处理器成功。
					if (authzHandler != null) {
						//构建ActionKey，参考ActionMapping中实现
						String actionKey = createActionKey(controllerClass, method, controllerKey);
						//添加映射
						authzMaps.put(actionKey, authzHandler);
					}
				}
			}
		}
		//注入到ShiroKit类中。ShiroKit类以单例模式运行。
		ShiroKit.init(authzMaps);
		/**
		 * 设定登录，登录成功，未授权等url地址
		 */
		ShiroKit.setLoginUrl(loginUrl);
		ShiroKit.setSuccessUrl(successUrl);
		ShiroKit.setUnauthorizedUrl(unauthorizedUrl);
		return true;
	}

	/**
	 * 从Controller方法中构建出需要排除的方法列表
	 * @return
	 */
	private Set<String> buildExcludedMethodName() {
		Set<String> excludedMethodName = new HashSet<String>();
		Method[] methods = Controller.class.getMethods();
		for (Method m : methods) {
			if (m.getParameterTypes().length == 0)
				excludedMethodName.add(m.getName());
		}
		return excludedMethodName;
	}

	/**
	 * 依据Controller的注解和方法的注解来生成访问控制处理器。
	 * @param controllerAnnotations  Controller的注解
	 * @param methodAnnotations 方法的注解
	 * @return 访问控制处理器
	 */
	private AuthzHandler createAuthzHandler(
			List<Annotation> controllerAnnotations,
			List<Annotation> methodAnnotations) {

		//没有注解
		if (controllerAnnotations.size() == 0 && methodAnnotations.size() == 0) {
			return null;
		}
		//至少有一个注解
		List<AuthzHandler> authzHandlers = new ArrayList<AuthzHandler>(5);
		for (int index = 0; index < 5; index++) {
			authzHandlers.add(null);
		}

		// 逐个扫描注解，若是相应的注解则在相应的位置赋值。
		scanAnnotation(authzHandlers, controllerAnnotations);
		// 逐个扫描注解，若是相应的注解则在相应的位置赋值。函数的注解优先级高于Controller
		scanAnnotation(authzHandlers, methodAnnotations);

		// 去除空值
		List<AuthzHandler> finalAuthzHandlers = new ArrayList<AuthzHandler>();
		for (AuthzHandler a : authzHandlers) {
			if (a != null) {
				finalAuthzHandlers.add(a);
			}
		}
		authzHandlers = null;
		// 存在多个，则构建组合AuthzHandler
		if (finalAuthzHandlers.size() > 1) {
			return new CompositeAuthzHandler(finalAuthzHandlers);
		}
		// 一个的话直接返回
		return finalAuthzHandlers.get(0);
	}

	/**
	 * 逐个扫描注解，若是相应的注解则在相应的位置赋值。
	 * 注解的处理是有顺序的，依次为RequiresRoles，RequiresPermissions，
	 * RequiresAuthentication，RequiresUser，RequiresGuest
	 *
	 * @param authzArray
	 * @param annotations
	 */
	private void scanAnnotation(List<AuthzHandler> authzArray,
			List<Annotation> annotations) {
		if (null == annotations || 0 == annotations.size()) {
			return;
		}
		for (Annotation a : annotations) {
			if (a instanceof RequiresRoles) {
				authzArray.set(0, new RoleAuthzHandler(a));
			} else if (a instanceof RequiresPermissions) {
				authzArray.set(1, new PermissionAuthzHandler(a));
			} else if (a instanceof RequiresAuthentication) {
				authzArray.set(2, AuthenticatedAuthzHandler.me());
			} else if (a instanceof RequiresUser) {
				authzArray.set(3, UserAuthzHandler.me());
			} else if (a instanceof RequiresGuest) {
				authzArray.set(4, GuestAuthzHandler.me());
			}
		}
	}

	/**
	 * 构建actionkey，参考ActionMapping中的实现。
	 *
	 * @param controllerClass
	 * @param method
	 * @param controllerKey
	 * @return
	 */
	private String createActionKey(Class<? extends Controller> controllerClass,
			Method method, String controllerKey) {
		String methodName = method.getName();
		String actionKey = "";

		ActionKey ak = method.getAnnotation(ActionKey.class);
		if (ak != null) {
			actionKey = ak.value().trim();
			if ("".equals(actionKey))
				throw new IllegalArgumentException(controllerClass.getName() + "." + methodName + "(): The argument of ActionKey can not be blank.");
			if (!actionKey.startsWith(SLASH))
				actionKey = SLASH + actionKey;
		}
		else if (methodName.equals("index")) {
			actionKey = controllerKey;
		}
		else {
			actionKey = controllerKey.equals(SLASH) ? SLASH + methodName : controllerKey + SLASH + methodName;
		}
		return actionKey;
	}

	/**
	 * 返回该方法的所有访问控制注解
	 *
	 * @param method
	 * @return
	 */
	private List<Annotation> getAuthzAnnotations(Method method) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES) {
			Annotation a = method.getAnnotation(annClass);
			if (a != null) {
				annotations.add(a);
			}
		}
		return annotations;
	}

	/**
	 * 返回该Controller的所有访问控制注解
	 *
	 * @param targetClass
	 * @return
	 */
	private List<Annotation> getAuthzAnnotations(
			Class<? extends Controller> targetClass) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES) {
			Annotation a = targetClass.getAnnotation(annClass);
			if (a != null) {
				annotations.add(a);
			}
		}
		return annotations;
	}
	/**
	 * 该方法上是否有ClearShiro注解
	 * @param method
	 * @return
	 */
	private boolean isClearShiroAnnotationPresent(Method method) {
		Annotation a = method.getAnnotation(ClearShiro.class);
		if (a != null) {
			return true;
		}
		return false;
	}

	/**
	 * 登录成功连接
	 * @param successUrl
	 */
	public final void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	/**
	 * 登录连接
	 * @param loginUrl
	 */
	public final void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * 未授权连接
	 * @param unauthorizedUrl
	 */
	public final void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}
}
