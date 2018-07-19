<#macro menu page_tab="">
<aside class="main-sidebar" style="position: fixed">
  <section class="sidebar">
    <ul class="sidebar-menu">
      <div class="user-panel">
        <div class="pull-left image">
          <img src="/static/AdminLTE/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>欢迎您, ${sec.principal!}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <li class="header">MAIN NAVIGATION</li>
      <#if sec.allGranted('dashboard')>
        <li <#if page_tab=='index'>class="active"</#if>>
          <a href="/admin/index">
            <i class="fa fa-dashboard"></i>
            <span>主页</span>
          </a>
        </li>
      </#if>
      <#if sec.allGranted('topic:list')>
        <li <#if page_tab=='topic'>class="active"</#if>>
          <a href="/admin/topic/list">
            <i class="fa fa-list"></i>
            <span>话题列表</span>
          </a>
        </li>
      </#if>
      <#if sec.allGranted('comment:list')>
        <li <#if page_tab=='comment'>class="active"</#if>>
          <a href="/admin/comment/list">
            <i class="fa fa-comment"></i>
            <span>评论列表</span>
          </a>
        </li>
      </#if>
      <#if sec.allGranted('tag:list')>
        <li <#if page_tab=='tag'>class="active"</#if>>
          <a href="/admin/tag/list">
            <i class="fa fa-list"></i>
            <span>标签列表</span>
          </a>
        </li>
      </#if>
      <#if sec.allGranted('user:list')>
        <li <#if page_tab=='user'>class="active"</#if>>
          <a href="/admin/user/list">
            <i class="fa fa-user"></i>
            <span>用户列表</span>
          </a>
        </li>
      </#if>
      <#if sec.allGranted('log:list')>
        <li <#if page_tab=='log'>class="active"</#if>>
          <a href="/admin/log/list">
            <i class="fa fa-list"></i>
            <span>日志列表</span>
          </a>
        </li>
      </#if>
      <li class="treeview <#if page_tab?index_of("admin_user_") == 0>active</#if>">
        <a href="#">
          <i class="fa fa-user"></i> <span>权限中心</span>
          <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
        </a>
        <ul class="treeview-menu">
          <#if sec.allGranted('admin_user:list')>
            <li <#if page_tab=='admin_user_list'>class="active"</#if>>
              <a href="/admin/admin_user/list">
                <i class="fa fa-circle-o"></i>
                 后台用户列表
              </a>
            </li>
          </#if>
          <#if sec.allGranted('permission:list')>
            <li <#if page_tab=='admin_user_role'>class="active"</#if>>
              <a href="/admin/role/list">
                <i class="fa fa-circle-o"></i>
                角色列表
              </a>
            </li>
          </#if>
          <#if sec.allGranted('role:list')>
            <li <#if page_tab=='admin_user_permission'>class="active"</#if>>
              <a href="/admin/permission/list">
                <i class="fa fa-circle-o"></i>
                权限列表
              </a>
            </li>
          </#if>
        </ul>
      </li>
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