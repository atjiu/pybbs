<#include "./common/layout.ftl">
<@html page_title="首页 - ${site.name!}" page_tab="admin">
<div class="row">
  <div class="col-md-2 hidden-sm hidden-xs">
    <#include "./components/admin_left.ftl">
    <@admin_left page_tab="index"/>
  </div>
  <div class="col-md-10">
    <div class="panel panel-default">
      <div class="panel-heading">仪表盘</div>
      <div class="panel-body">欢迎来到后台管理页面！</div>
    </div>
  </div>
</div>
</@html>