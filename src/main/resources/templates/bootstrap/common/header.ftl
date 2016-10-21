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
      <#if _search == true>
        <form class="navbar-form navbar-left" role="search" action="/search" method="get">
          <div class="form-group">
            <input type="text" class="form-control" name="q" value="${q!}" placeholder="回车搜索">
          </div>
        </form>
      </#if>
      <ul class="nav navbar-nav navbar-right">
        <li <#if page_tab == 'donate'> class="active" </#if>>
          <a href="/donate">捐赠</a>
        </li>
        <li <#if page_tab == 'api'> class="active" </#if>>
          <a href="/apidoc">API</a>
        </li>
        <li <#if page_tab == 'about'> class="active" </#if>>
          <a href="/about">关于</a>
        </li>
        <#if _isAuthenticated?? && _isAuthenticated == true>
          <li class="hidden-md hidden-lg">
            <a href="/topic/create">发布话题</a>
          </li>
          <li <#if page_tab == 'notification'> class="active" </#if>>
            <a href="/notification/list">通知 <span class="badge" id="badge"></span></a>
            <script>
              setInterval(function () {
                $.ajax({
                  url: "/api/notification/notRead",
                  async: true,
                  cache: false,
                  type: "get",
                  dataType: "json",
                  success: function (data) {
                    if(data.code == 200 && data.detail > 0) {
                      $("#badge").text(data.detail);
                    }
                  }
                })
              }, 120000)
            </script>
          </li>
          <li <#if page_tab == 'user'> class="active" </#if>>
            <a href="/user/${_principal}">
            ${_principal}
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
              <#if _roles?seq_contains("admin:index")>
                <li><a href="/admin/index">进入后台</a></li>
              </#if>
              <li><a href="/logout">退出</a></li>
            </ul>
          </li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if>><a href="/login">登录</a></li>
          <li <#if page_tab == "register">class="active"</#if>><a href="/register">注册</a></li>
        </#if>
      </ul>
    </div>
  </div>
</nav>
</#macro>