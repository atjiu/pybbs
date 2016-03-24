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

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * 认证通过或已记住的用户访问控制处理器
 * 单例模式运行。
 * @author dafei
 *
 */
class UserAuthzHandler extends AbstractAuthzHandler {
	private static UserAuthzHandler uah = new UserAuthzHandler();

	private UserAuthzHandler(){}

	public static  UserAuthzHandler me(){
		return uah;
	}

	public void assertAuthorized() throws AuthorizationException {
		if (getSubject().getPrincipal() == null) {
            throw new UnauthenticatedException("Attempting to perform a user-only operation.  The current Subject is " +
                    "not a user (they haven't been authenticated or remembered from a previous login).  " +
                    "Access denied.");
        }
	}
}
