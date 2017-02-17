<#include "../../common/layout.ftl">
<@html>
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="indexed"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><@spring.message "site.panel.header.admin.indexed.index"/></div>
      <div class="panel-body">
        <#if s?? && s == "add">
          <div class="alert alert-success"><@spring.message "site.prompt.admin.indexed.indexedTopics"/></div>
        </#if>
        <#if s?? && s == "del">
          <div class="alert alert-danger"><@spring.message "site.prompt.admin.indexed.deleteIndexed"/></div>
        </#if>
        <a href="/admin/indexed/indexAll" class="btn btn-sm btn-primary"><@spring.message "site.button.admin.indexed.indexedTopics"/></a>
        <a href="/admin/indexed/deleteAll" class="btn btn-sm btn-danger"><@spring.message "site.button.admin.indexed.deleteIndexed"/></a>
      </div>
    </div>
  </div>
</div>
</@html>