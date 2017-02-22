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
      <a class="navbar-brand" style="color:#fff;" href="/"><@spring.message "site.name"/></a>
    </div>
    <div id="navbar" class="navbar-collapse collapse header-navbar">
      <#if _search?? && _search == "true">
        <form class="navbar-form navbar-left" role="search" action="/search" method="get">
          <div class="form-group">
            <input type="text" class="form-control" name="q" value="${q!}" placeholder="<@spring.message "site.menu.search"/>">
          </div>
        </form>
      </#if>-
      <ul class="nav navbar-nav navbar-right">
        <#--<li <#if page_tab == 'api'> class="active" </#if>>-->
          <#--<a href="/apidoc"><@spring.message "site.menu.api"/></a>-->
        <#--</li>-->
        <li <#if page_tab == 'about'> class="active" </#if>>
          <a href="/about"><@spring.message "site.menu.about"/></a>
        </li>
        <#if _isAuthenticated?? && _isAuthenticated == true>
          <li class="hidden-md hidden-lg">
            <a href="/topic/create"><@spring.message "site.menu.create"/></a>
          </li>
          <li <#if page_tab == 'notification'> class="active" </#if>>
            <a href="/notification/list"><@spring.message "site.menu.notifications"/> <span class="badge" id="badge"></span></a>
            <script>
              function notificationCount() {
                $.ajax({
                  url: "/notification/notRead",
                  async: true,
                  cache: false,
                  type: "get",
                  dataType: "json",
                  success: function (data) {
                    if(data.code == 200 && data.detail > 0) {
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
            <a href="/user/${_principal}">
            ${_principal}
              <span class="badge" id="badge"></span>
            </a>
          </li>
          <li <#if page_tab == 'setting'> class="active" </#if>>
            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"
               data-hover="dropdown">
              <@spring.message "site.menu.settings"/>
              <span class="caret"></span>
            </a>
            <span class="dropdown-arrow"></span>
            <ul class="dropdown-menu">
              <li><a href="/user/setting"><@spring.message "site.menu.user.profile"/></a></li>
              <#if _roles?seq_contains("admin:index")>
                <li><a href="/admin/index"><@spring.message "site.menu.admin"/></a></li>
              </#if>
              <li><a href="/logout"><@spring.message "site.menu.logout"/></a></li>
            </ul>
          </li>
        <#else>
          <li <#if page_tab == "login">class="active"</#if>><a href="/login"><@spring.message "site.menu.login"/></a></li>
          <li <#if page_tab == "register">class="active"</#if>><a href="/register"><@spring.message "site.menu.register"/></a></li>
        </#if>
      </ul>
    </div>
  </div>
</nav>
</#macro>