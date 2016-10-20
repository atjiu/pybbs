<#include "../../common/layout.ftl"/>
<@html page_title="角色管理" page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="role"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        角色管理
        <#if _roles?seq_contains("role:add")>
          <a href="/admin/role/add" class="pull-right">添加角色</a>
        </#if>
      </div>
      <div class="table-responsive">
        <table class="table table-striped table-responsive">
          <tbody>
            <#list roles as role>
            <tr>
              <td>${role.id}</td>
              <td>${role.name!}</td>
              <td>${role.description!}</td>
              <td>
                <#if _roles?seq_contains("role:edit")>
                  <a href="/admin/role/${role.id}/edit" class="btn btn-xs btn-warning">配置权限</a>
                </#if>
                <#if _roles?seq_contains("role:delete")>
                  <a href="javascript:if(confirm('确认删除吗?')) location.href='/admin/role/${role.id}/delete'"
                     class="btn btn-xs btn-danger">删除</a>
                </#if>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</@html>