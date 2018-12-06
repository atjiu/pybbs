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
        <li <#if page_tab == "index">class="active"</#if>><a href="/">首页</a></li>
        <li <#if page_tab == "tags">class="active"</#if>><a href="/tags">标签</a></li>
      </ul>
      <#--<form class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>-->
      <ul class="nav navbar-nav navbar-right">
        <li <#if page_tab == "api">class="active"</#if>><a href="/api">API</a></li>
        <#if _user??>
          <li <#if page_tab == "notification">class="active"</#if>><a href="/notifications">通知</a></li>
          <li <#if page_tab == "user">class="active"</#if>><a href="/user/${_user.username}">${_user.username}</a></li>
          <li <#if page_tab == "settings">class="active"</#if>><a href="/settings">设置</a></li>
          <li><a href="javascript:if(confirm('确定要登出吗？登出了就没办法发帖回帖了哦!'))window.location.href='/logout'">登出</a></li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if>><a href="/login">登录</a></li>
          <li <#if page_tab == "register">class="active"</#if>><a href="/register">注册</a></li>
        </#if>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</#macro>
