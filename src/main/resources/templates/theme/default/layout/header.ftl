<#macro header page_tab>
<nav class="navbar navbar-default" style="border-radius: 0">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">${site.name}</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li <#if page_tab == "index">class="active"</#if>><a href="/"><i class="fa fa-home"></i> ${i18n.getMessage("index")}</a></li>
        <li <#if page_tab == "tags">class="active"</#if>><a href="/tags"><i class="fa fa-tags"></i> ${i18n.getMessage("tag")}</a></li>
      </ul>
      <#if site.search == "1">
        <form class="navbar-form navbar-left" action="/search" method="get">
          <div class="form-group">
            <input type="text" name="keyword" value="${keyword!}" class="form-control" required placeholder="Search">
          </div>
          <button type="submit" class="btn btn-info">${i18n.getMessage("search")}</button>
        </form>
      </#if>
      <ul class="nav navbar-nav navbar-right">
        <#--<li <#if page_tab == "api">class="active"</#if>><a href="/api">API</a></li>-->
        <#if _user??>
          <li <#if page_tab == "notification">class="active"</#if>><a href="/notifications"><i class="fa fa-envelope"></i> ${i18n.getMessage("notification")} <span class="badge badge-default" id="nh_count"></span></a></li>
          <li <#if page_tab == "user">class="active"</#if>><a href="/user/${_user.username}"><i class="fa fa-user"></i> ${_user.username}</a></li>
          <li <#if page_tab == "settings">class="active"</#if>><a href="/settings"><i class="fa fa-cog"></i> ${i18n.getMessage("setting")}</a></li>
          <li><a href="javascript:if(confirm('确定要登出吗？登出了就没办法发帖回帖了哦!'))window.location.href='/logout'"><i class="fa fa-sign-out"></i> ${i18n.getMessage("logout")}</a></li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if>><a href="/login"><i class="fa fa-sign-in"></i> ${i18n.getMessage("login")}</a></li>
          <li <#if page_tab == "register">class="active"</#if>><a href="/register"><i class="fa fa-sign-out"></i> ${i18n.getMessage("register")}</a></li>
          <#if !model.isEmpty(site.oauth_github_client_id!) && !model.isEmpty(site.oauth_github_client_secret!)>
            <li><a href="/oauth/github"><i class="fa fa-github"></i> ${i18n.getMessage("github_login")}</a></li>
          </#if>
        </#if>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</#macro>
