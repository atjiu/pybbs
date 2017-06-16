<#macro header page_tab="">
<nav class="navbar navbar-inverse" style="border-radius: 0">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
              aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" style="color:#fff;" href="/">${site.name!}</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse header-navbar">
      <ul class="nav navbar-nav">
        <li <#if page_tab == "index">class="active"</#if>>
          <a href="/">首页</a>
        </li>
        <li <#if page_tab == "label">class="active"</#if>>
          <a href="/label/list">标签</a>
        </li>
      </ul>
      <#if site.search?? && site.search == true>
        <form class="navbar-form navbar-left" role="search" action="/search" method="get">
          <div class="form-group">
            <input type="text" class="form-control" name="q" value="${q!}" placeholder="回车搜索">
          </div>
        </form>
      </#if>-
      <ul class="nav navbar-nav navbar-right">
        <#if sec.isAuthenticated()>
          <li class="hidden-md hidden-lg">
            <a href="/topic/create">发布话题</a>
          </li>
          <li <#if page_tab == 'notification'> class="active" </#if>>
            <a href="/notification/list">通知 <span class="badge" id="badge"></span></a>
            <script>
              function notificationCount() {
                $.ajax({
                  url: "/notification/notRead",
                  async: true,
                  cache: false,
                  type: "get",
                  dataType: "json",
                  success: function (data) {
                    if(data.code === 200 && data.detail > 0) {
                      $("#badge").text(data.detail);
                    }
                  }
                });
              }
              notificationCount();
              setInterval(function () {
                notificationCount();
              }, 120000);
            </script>
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
          <li><a href="javascript:if(confirm('确定要退出吗？'))location.href='/logout'">退出</a></li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if>><a href="/login">登录</a></li>
          <li <#if page_tab == "register">class="active"</#if>><a href="/register">注册</a></li>
        </#if>
      </ul>
    </div>
  </div>
</nav>
</#macro>