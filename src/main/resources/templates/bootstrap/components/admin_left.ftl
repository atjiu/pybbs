<#macro admin_left page_tab>
<div class="list-group">
  <#if _roles?seq_contains("admin:index")>
    <a href="/admin/index" class="list-group-item <#if page_tab == "index">active</#if>"><@spring.message "site.menu.admin.dashboard"/></a>
  </#if>
  <#if _roles?seq_contains("section:list")>
    <a href="/admin/section/list" class="list-group-item <#if page_tab == "section">active</#if>"><@spring.message "site.menu.admin.sections"/></a>
  </#if>
  <#if _roles?seq_contains("reply:list")>
    <a href="/admin/reply/list" class="list-group-item <#if page_tab == "reply">active</#if>"><@spring.message "site.menu.admin.comments"/></a>
  </#if>
  <#if _roles?seq_contains("user:list")>
    <a href="/admin/user/list" class="list-group-item <#if page_tab == "user">active</#if>"><@spring.message "site.menu.admin.users"/></a>
  </#if>
  <#if _roles?seq_contains("role:list")>
    <a href="/admin/role/list" class="list-group-item <#if page_tab == "role">active</#if>"><@spring.message "site.menu.admin.roles"/></a>
  </#if>
  <#if _roles?seq_contains("permission:list")>
    <a href="/admin/permission/list" class="list-group-item <#if page_tab == "permission">active</#if>"><@spring.message "site.menu.admin.permissions"/></a>
  </#if>
  <#if _roles?seq_contains("index:all") || _roles?seq_contains("index:deleteAll")>
    <a href="/admin/indexed" class="list-group-item <#if page_tab == "indexed">active</#if>"><@spring.message "site.menu.admin.indexed"/></a>
  </#if>
  <#if _roles?seq_contains("setting:detail")>
    <a href="/admin/setting/detail" class="list-group-item <#if page_tab == "setting">active</#if>"><@spring.message "site.menu.admin.setting"/></a>
  </#if>
</div>
</#macro>