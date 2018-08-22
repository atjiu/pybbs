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
      <a data-pjax href="/" class="navbar-brand" style="font-weight: 700; font-size: 27px;">${site.name!}</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse header-navbar">
      <#if site.search?? && site.search == true>
        <form class="navbar-form navbar-left hidden-xs hidden-sm" role="search" action="/search" method="get">
          <div class="form-group has-feedback">
            <input type="text" class="form-control" name="keyword" value="${keyword!}" style="width: 270px;"
                   placeholder="回车搜索">
            <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
          </div>
        </form>
      </#if>
      <ul class="nav navbar-nav navbar-right">
        <li class="hidden-xs <#if page_tab == "index">active</#if>" data-tab="index">
          <a data-pjax href="/">首页</a>
        </li>
        <li class="hidden-xs <#if page_tab == "tags">active</#if>" data-tab="tags">
          <a data-pjax href="/tags">标签</a>
        </li>
        <#if user??>
          <li class="hidden-md hidden-lg">
            <a data-pjax href="/topic/create">发布话题</a>
          </li>
          <li <#if page_tab == 'user'> class="active" </#if> data-tab="user">
            <a data-pjax href="/user/${user.username!}">
              ${user.username!}
              <span class="badge" id="badge"></span>
            </a>
          </li>
          <li <#if page_tab == 'setting'> class="active" </#if> data-tab="setting"><a data-pjax href="/user/setting/profile">设置</a></li>
          <li><a href="javascript:if(confirm('确定要登出${site.name!}吗？'))location.href='/logout'">退出</a></li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if> data-tab="login"><a data-pjax href="/login">登录</a></li>
          <li <#if page_tab == "register">class="active"</#if> data-tab="register"><a data-pjax href="/register">注册</a></li>
        </#if>
      </ul>
    </div>
  </div>
</nav>
  <#if user?? && site.socketNotification>
    <script src="/static/js/socket.io.js"></script>
    <script>
      var socket = io.connect('${site.socket.url!}?username=${user.username!}');
      // socket.on('connect', function () {});
      // socket.on('disconnect', function () {});
      var title = document.title;
      socket.on('notification', function (data) {
        if (data.message) toast(data.message);
        if (data.count > 0) {
          $("#n_count").text(data.count);
          document.title = "(" + data.count + ") " + title;
        }
      });
    </script>
  </#if>
</#macro>