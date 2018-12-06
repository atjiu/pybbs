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
        <p>欢迎您, ${sec.principal!}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <li class="header">MAIN NAVIGATION</li>
      <#if sec.hasPermission('index:index')>
        <li <#if page_tab=='index'>class="active"</#if>>
          <a href="/admin/index">
            <i class="fa fa-dashboard"></i>
            <span>仪表盘</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('topic:list')>
        <li <#if page_tab=='topic'>class="active"</#if>>
          <a href="/admin/topic/list">
            <i class="fa fa-list"></i>
            <span>话题列表</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('comment:list')>
        <li <#if page_tab=='comment'>class="active"</#if>>
          <a href="/admin/comment/list">
            <i class="fa fa-comment"></i>
            <span>评论列表</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('tag:list')>
        <li <#if page_tab=='tag'>class="active"</#if>>
          <a href="/admin/tag/list">
            <i class="fa fa-tags"></i>
            <span>标签列表</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermission('user:list')>
        <li <#if page_tab=='user'>class="active"</#if>>
          <a href="/admin/user/list">
            <i class="fa fa-user"></i>
            <span>用户列表</span>
          </a>
        </li>
      </#if>
      <#if sec.hasPermissionOr('admin_user:list', 'role:list', 'permission:list')>
        <li class="treeview <#if page_tab?index_of("auth_") != -1>active</#if>">
          <a href="#">
            <i class="fa fa-server"></i> <span>权限中心</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
            <#if sec.hasPermission('admin_user:list')>
              <li <#if page_tab=='auth_admin_user'>class="active"</#if>>
                <a href="/admin/admin_user/list">
                  <i class="fa fa-circle-o"></i>
                  后台用户列表
                </a>
              </li>
            </#if>
            <#if sec.hasPermission('role:list')>
            <li <#if page_tab=='auth_role'>class="active"</#if>>
              <a href="/admin/role/list">
                <i class="fa fa-circle-o"></i>
                角色列表
              </a>
            </li>
            </#if>
            <#if sec.hasPermission('permission:list')>
            <li <#if page_tab=='auth_permission'>class="active"</#if>>
              <a href="/admin/permission/list">
                <i class="fa fa-circle-o"></i>
                权限列表
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
            <span>系统设置</span>
          </a>
        </li>
      </#if>
      <li>
        <a href="/admin/logout">
          <i class="fa fa-sign-out"></i>
          <span>退出</span>
        </a>
      </li>
    </ul>
  </section>
  <!-- /.sidebar -->
</aside>
<!-- /.control-sidebar -->
</#macro>
