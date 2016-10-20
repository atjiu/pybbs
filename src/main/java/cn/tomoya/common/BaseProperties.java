package cn.tomoya.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
//@Component
//@Setter @Getter
public class BaseProperties {

    @Value("${cn.tomoya.bbs.siteName}")
    private String siteName;

    @Value("${cn.tomoya.bbs.cookie.name}")
    private String cookieName;

    @Value("${cn.tomoya.bbs.cookie.domain}")
    private String cookieDomain;

    @Value("${cn.tomoya.bbs.session.name}")
    private String sessionName;

}
