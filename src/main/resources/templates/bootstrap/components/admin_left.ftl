<#macro admin_left page_tab>
<div class="list-group">
  <#if _roles?seq_contains("admin:index")>
    <a href="/admin/index" class="list-group-item <#if page_tab == "index">active</#if>">仪表盘</a>
  </#if>
  <#if _roles?seq_contains("reply:list")>
    <a href="/admin/reply/list" class="list-group-item <#if page_tab == "reply">active</#if>">回复管理</a>
  </#if>
  <#if _roles?seq_contains("user:list")>
    <a href="/admin/user/list" class="list-group-item <#if page_tab == "user">active</#if>">用户管理</a>
  </#if>
  <#if _roles?seq_contains("role:list")>
    <a href="/admin/role/list" class="list-group-item <#if page_tab == "role">active</#if>">角色管理</a>
  </#if>
  <#if _roles?seq_contains("permission:list")>
    <a href="/admin/permission/list" class="list-group-item <#if page_tab == "permission">active</#if>">权限管理</a>
  </#if>
  <#if _roles?seq_contains("index:all") || _roles?seq_contains("index:deleteAll")>
    <a href="/admin/indexed" class="list-group-item <#if page_tab == "indexed">active</#if>">索引管理</a>
  </#if>
</div>
</#macro>