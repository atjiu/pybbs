<#macro menu page_tab="">
<aside class="main-sidebar" style="position: fixed">
  <section class="sidebar">
    <ul class="sidebar-menu">
      <div class="user-panel">
        <div class="pull-left image">
          <img src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.4.8/img/user2-160x160.jpg" class="img-circle"
               alt="User Image">
        </div>
        <div class="pull-left info">
        <p>${i18n.getMessage("welcome")}, ${sec.principal!}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <li class="header">MAIN NAVIGATION</li>
      <#if sec.hasPermission('index:index')>
        <li <#if page_tab=='index'>class="active"</#if>>
          <a href="/admin/index">
            <i class="fa fa-dashboard"></i>
            <span>${i18n.getMessage("admin.dashboard")}</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('topic:list')>
        <li <#if page_tab=='topic'>class="active"</#if>>
          <a href="/admin/topic/list">
            <i class="fa fa-list"></i>
            <span>${i18n.getMessage("admin.topics")}</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('comment:list')>
        <li <#if page_tab=='comment'>class="active"</#if>>
          <a href="/admin/comment/list">
            <i class="fa fa-comment"></i>
            <span>${i18n.getMessage("admin.comments")}</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('tag:list')>
        <li <#if page_tab=='tag'>class="active"</#if>>
          <a href="/admin/tag/list">
            <i class="fa fa-tags"></i>
            <span>${i18n.getMessage("admin.tags")}</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('user:list')>
        <li <#if page_tab=='user'>class="active"</#if>>
          <a href="/admin/user/list">
            <i class="fa fa-user"></i>
            <span>${i18n.getMessage("admin.users")}</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermissionOr('admin_user:list', 'role:list', 'permission:list')>
        <li class="treeview <#if page_tab?index_of("auth_") != -1>active</#if>">
          <a href="#">
            <i class="fa fa-server"></i> <span>${i18n.getMessage("admin.permission_config")}</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
            <#if sec.hasPermission('admin_user:list')>
              <li <#if page_tab=='auth_admin_user'>class="active"</#if>>
                <a href="/admin/admin_user/list">
                  <i class="fa fa-circle-o"></i>
                  ${i18n.getMessage("admin.admin_users")}
                </a>
              </li>
            </#if>
            <#if sec.hasPermission('role:list')>
            <li <#if page_tab=='auth_role'>class="active"</#if>>
              <a href="/admin/role/list">
                <i class="fa fa-circle-o"></i>
                ${i18n.getMessage("admin.roles")}
              </a>
            </li>
            </#if>
            <#if sec.hasPermission('permission:list')>
            <li <#if page_tab=='auth_permission'>class="active"</#if>>
              <a href="/admin/permission/list">
                <i class="fa fa-circle-o"></i>
                ${i18n.getMessage("admin.permissions")}
              </a>
            </li>
            </#if>
          </ul>
        </li>
      </#if>
      <#if sec.hasPermission('system:edit')>
        <li <#if page_tab=='system'>class="active"</#if>>
          <a href="/admin/system/edit">
            <i class="fa fa-cogs"></i>
            <span>${i18n.getMessage("admin.system_config")}</span>
          </a>
        </li>
      </#if>
      <li>
        <a href="/admin/logout">
          <i class="fa fa-sign-out"></i>
          <span>${i18n.getMessage("logout")}</span>
        </a>
      </li>
    </ul>
  </section>
  <!-- /.sidebar -->
</aside>
<!-- /.control-sidebar -->
</#macro>
