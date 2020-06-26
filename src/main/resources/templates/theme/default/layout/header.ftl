<#macro header page_tab>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/">${site.name!}</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
            <div class="d-flex justify-content-start">
                <ul class="navbar-nav">
                    <li class="nav-item <#if page_tab == "index">active</#if>">
                        <a href="/" class="nav-link">
                            <i class="fa fa-home"></i> ${i18n.getMessage("index")}
                        </a>
                    </li>
                    <li class="nav-item <#if page_tab == "tags">active</#if>">
                        <a href="/tags" class="nav-link">
                            <i class="fa fa-tags"></i> ${i18n.getMessage("tag")}
                        </a>
                    </li>
                </ul>
                <form class="form-inline my-2 my-lg-0 ml-2 d-none d-md-block" action="/search">
                    <div class="input-group">
                        <input class="form-control" type="search" name="keyword" placeholder="回车搜索" value="${keyword!}"
                               required aria-label="Search">
                        <div class="input-group-append">
                            <button class="btn btn-outline-success" type="submit">${i18n.getMessage("search")}</button>
                        </div>
                    </div>
                </form>
            </div>
            <ul class="navbar-nav">
                <#if _user??>
                    <li class="nav-item <#if page_tab == "notification">active</#if>">
                        <a href="/notifications" class="nav-link">
                            <i class="fa fa-envelope"></i> ${i18n.getMessage("notification")}
                            <span class="badge badge-default" id="nh_count"></span>
                        </a>
                    </li>
                    <li class="nav-item <#if page_tab == "user">active</#if>">
                        <a href="/user/${_user.username}" class="nav-link">
                            <i class="fa fa-user"></i> ${_user.username}
                        </a>
                    </li>
                    <li class="nav-item <#if page_tab == "settings">active</#if>">
                        <a href="/settings" class="nav-link">
                            <i class="fa fa-cog"></i> ${i18n.getMessage("setting")}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="javascript:if(confirm('确定要登出吗？登出了就没办法发帖回帖了哦!'))window.location.href='/logout'"
                           class="nav-link">
                            <i class="fa fa-sign-out"></i> ${i18n.getMessage("logout")}
                        </a>
                    </li>
                <#else>
                    <li class="nav-item <#if page_tab == "login">active</#if>">
                        <a href="/login" class="nav-link">
                            <i class="fa fa-sign-in"></i> ${i18n.getMessage("login")}
                        </a>
                    </li>
                </#if>
            </ul>
        </div>
    </nav>
</#macro>
