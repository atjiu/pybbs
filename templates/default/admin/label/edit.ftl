<#include "../common/layout.ftl"/>
<@html page_title="标签编辑" page_tab="admin">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="label"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        标签编辑
      </div>
      <div class="panel-body">
        <form method="post" action="/admin/label/update" enctype="multipart/form-data">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="hidden" name="id" value="${label.id}"/>
          <div class="form-group">
            <label for="name">名称</label>
            <input type="text" class="form-control" id="name" name="name" value="${label.name}" placeholder="名称">
          </div>
          <div class="form-group">
            <label for="intro">介绍</label>
            <textarea class="form-control" id="intro" name="intro" placeholder="介绍">${label.intro!}</textarea>
          </div>
          <div class="form-group">
            <label for="logo">LOGO</label>
            <input type="file" id="logo" name="file"/>
            <#if label.image??>
              <div class="mar-top-5"></div>
              <img src="${label.image}" class="avatar-lg"/>
            </#if>
          </div>
          <button type="submit" class="btn btn-default btn-sm">提交</button>
        </form>
      </div>
    </div>
  </div>
</div>
</@html>