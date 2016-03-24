package com.jfinalbbs.module.user.realm;

import com.jfinalbbs.module.user.AdminUser;
import com.jfinalbbs.module.user.Permission;
import com.jfinalbbs.module.user.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(Role.me.findRoles(username));
        authorizationInfo.setStringPermissions(Permission.me.findPermissions(username));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        AdminUser adminUser = AdminUser.me.findByUsername(username);

        if(adminUser == null) {
            throw new UnknownAccountException();//没找到帐号
        }

//        if(Boolean.TRUE.equals(user.getLocked())) {
//            throw new LockedAccountException(); //帐号锁定
//        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                adminUser.getStr("username"), //用户名
                adminUser.getStr("password"), //密码
                ByteSource.Util.bytes(adminUser.getStr("username") + adminUser.getStr("salt")),
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
