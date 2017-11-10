<#macro header page_tab="">
<nav class="navbar navbar-default" style="border-radius: 0; margin-bottom: 10px;">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
              aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" style="font-weight: 700; font-size: 27px;" href="/">${site.name!}</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse header-navbar">
      <#if site.search?? && site.search == true>
        <form class="navbar-form navbar-left hidden-xs hidden-sm" role="search" action="/search" method="get">
          <div class="form-group has-feedback">
            <input type="text" class="form-control" name="q" value="${q!}" style="width: 270px;" placeholder="回车搜索">
            <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
          </div>
        </form>
      </#if>
      <ul class="nav navbar-nav navbar-right">
        <li class="hidden-xs <#if page_tab == "index">active</#if>">
          <a href="/">首页</a>
        </li>
        <#if sec.isAuthenticated()>
          <li class="hidden-md hidden-lg">
            <a href="/topic/create">发布话题</a>
          </li>
          <li <#if page_tab == 'user'> class="active" </#if>>
            <a href="/user/${sec.getPrincipal()!}">
              ${sec.getPrincipal()!}
              <span class="badge" id="badge"></span>
            </a>
          </li>
          <li <#if page_tab == 'setting'> class="active" </#if>><a href="/user/profile">设置</a></li>
          <#if sec.allGranted("admin:index")>
            <li <#if page_tab == 'admin'> class="active" </#if>><a href="/admin/index">进入后台</a></li>
          </#if>
          <li><a href="javascript:if(confirm('确定要登出${site.name!}吗？'))location.href='/logout'">退出</a></li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if>><a href="/login">登录</a></li>
          <li <#if page_tab == "register">class="active"</#if>><a href="/register">注册</a></li>
        </#if>
      </ul>
    </div>
  </div>
</nav>
</#macro>