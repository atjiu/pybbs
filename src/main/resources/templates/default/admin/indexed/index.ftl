<#include "../common/layout.ftl">
<@html page_title="索引 - ${site.name!}" page_tab="admin">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="indexed"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">索引</div>
      <div class="panel-body">
        <div class="alert alert-danger">索引功能都还没写，想用solr来实现的，可以参照pybbs2.3里用的检索！</div>
        <#if s?? && s == "add">
          <div class="alert alert-success">索引全部话题成功</div>
        </#if>
        <#if s?? && s == "del">
          <div class="alert alert-danger">删除全部索引成功</div>
        </#if>
        <a href="/admin/indexed/indexAll" class="btn btn-sm btn-primary">索引全部话题</a>
        <a href="/admin/indexed/deleteAll" class="btn btn-sm btn-danger">删除全部索引</a>
      </div>
    </div>
  </div>
</div>
</@html>