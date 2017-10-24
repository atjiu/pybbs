<#include "../common/layout.ftl"/>
<@html page_title="角色管理" page_tab="admin">
<div class="row">
  <div class="col-md-2 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="permission"/>
  </div>
  <div class="col-md-10">
    <div class="row" style="margin: 0 -5px;">
      <div class="col-md-10">
        <div class="panel panel-default">
          <div class="panel-heading">
            权限管理
            <#if sec.allGranted("permission:add")>
              <a class="pull-right" href="/admin/permission/add?pid=${pid!}">添加权限</a>
            </#if>
          </div>
          <div class="table-responsive">
            <table class="table table-striped table-responsive">
              <thead>
              <tr>
                <th>权限标识</th>
                <th>授权地址</th>
                <th>权限描述</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody>
                <#list childPermissions as permission>
                <tr>
                <#--<td>${permission.id!}</td>-->
                  <td>${permission.name!}</td>
                  <td>${permission.url!}</td>
                  <td>${permission.description!}</td>
                  <td>
                    <#if sec.allGranted("permission:edit")>
                      <a href="/admin/permission/${permission.id}/edit" class="btn btn-xs btn-warning">编辑</a>
                    </#if>
                    <#if sec.allGranted("permission:delete")>
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
                <#if sec.allGranted("permission:delete")>
                  <a href="javascript:if(confirm('确认删除吗?'))location.href='/admin/permission/${permission.id}/delete'">
                    <span class="text-danger glyphicon glyphicon-trash"></span>
                  </a>&nbsp;
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