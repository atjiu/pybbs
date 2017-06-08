<#include "../common/layout.ftl"/>
<@html page_tab="system" page_title="编辑板块">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="section"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 编辑板块
      </div>
      <div class="panel-body">
        <form action="/admin/section/${section.id!}/edit" method="post" id="sectionForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="name">名称</label>
            <input type="text" name="name" id="name" value="${section.name}" placeholder="名称" class="form-control">
          </div>
          <button onclick="saveSection()" id="saveSectionBtn" class="btn btn-sm btn-default">保存</button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>