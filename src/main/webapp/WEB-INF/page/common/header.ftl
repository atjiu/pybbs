<#macro header page_tab="">
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color:#fff;" href="/">${siteTitle!}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse header-navbar">
            <ul class="nav navbar-nav navbar-right">
                <li <#if page_tab == 'donate'> class="active" </#if>>
                    <a href="/donate">捐赠</a>
                </li>
                <li <#if page_tab == 'about'> class="active" </#if>>
                    <a href="/about">关于</a>
                </li>
                <#if userinfo??>
                    <li <#if page_tab == 'notification'> class="active" </#if>>
                        <a href="/notification">通知 <span class="badge" id="badge">${notifications!}</span></a>
                    </li>
                    <li <#if page_tab == 'user'> class="active" </#if>>
                        <a href="/user/${userinfo.nickname!}">
                            ${userinfo.nickname!}
                            <span class="badge" id="badge"></span>
                        </a>
                    </li>
                    <li <#if page_tab == 'setting'> class="active" </#if>>
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"
                           data-hover="dropdown">
                            设置
                            <span class="caret"></span>
                        </a>
                        <span class="dropdown-arrow"></span>
                        <ul class="dropdown-menu">
                            <li><a href="/user/setting">个人资料</a></li>
                            <@py.hasPermission name="system:users" id="${userinfo.id!}">
                                <li><a href="/manage/users">用户管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:roles" id="${userinfo.id!}">
                                <li><a href="/manage/roles">角色管理</a></li>
                            </@py.hasPermission>
                            <@py.hasPermission name="system:permissions" id="${userinfo.id!}">
                                <li><a href="/manage/permissions">权限管理</a></li>
                            </@py.hasPermission>
                            <li><a href="/logout">退出</a></li>
                        </ul>
                    </li>
                <#else>
                    <li>
                        <a href="/oauth/githublogin">Github登录</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
</#macro>