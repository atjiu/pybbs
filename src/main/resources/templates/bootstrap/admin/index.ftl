<#include "../common/layout.ftl">
<@html>
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="index"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><@spring.message "site.panel.header.admin.index"/></div>
      <div class="panel-body"><@spring.message "site.panel.body.admin.index"/></div>
    </div>
  </div>
</div>
</@html>