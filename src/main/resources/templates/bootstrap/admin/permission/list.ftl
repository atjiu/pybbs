<#include "../../common/layout.ftl"/>
<@html page_title="角色管理" page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="permission"/>
  </div>
  <div class="col-md-9">
    <div class="row">
      <div class="col-md-10">
        <div class="panel panel-default">
          <div class="panel-heading">
            权限管理
            <#if _roles?seq_contains("permission:add")>
              <a class="pull-right" href="/admin/permission/add?pid=${pid!}">添加权限</a>
            </#if>
          </div>
          <div class="table-responsive">
            <table class="table table-striped table-responsive">
              <tbody>
                <#list childPermissions as permission>
                <tr>
                  <td>${permission.id!}</td>
                  <td>${permission.name!}</td>
                  <td>${permission.url!}</td>
                  <td>${permission.description!}</td>
                  <td>
                    <#if _roles?seq_contains("permission:edit")>
                      <a href="/admin/permission/${permission.id}/edit" class="btn btn-xs btn-warning">编辑</a>
                    </#if>
                    <#if _roles?seq_contains("permission:delete")>
                      <a href="javascript:if(confirm('确认删除吗?')) location.href='/admin/permission/${permission.id!}/delete'"
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
      <div class="col-md-2">
        <div class="panel panel-default">
          <div class="panel-heading">父节点</div>
          <div class="list-group">
            <#list permissions as permission>
              <li class="list-group-item permission-item <#if pid?? && pid == permission.id>active</#if>">
                <#if _roles?seq_contains("permission:delete")>
                  <a href="javascript:if(confirm('确认删除吗?'))location.href='/admin/permission/${permission.id}/delete'">删除</a>
                </#if>
                <a href="/admin/permission/list?pid=${permission.id!}">
                  ${permission.description!}
                </a>
              </li>
            </#list>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</@html>