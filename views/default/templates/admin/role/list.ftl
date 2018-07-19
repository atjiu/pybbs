<#include "../layout/layout.ftl">
<@html page_title="角色列表" page_tab="admin_user_role">
  <section class="content-header">
    <h1>
      角色
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/role/list">角色</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">角色列表</h3>
        <#if sec.allGranted('role:add')>
          <a href="/admin/role/add" class="btn btn-sm btn-primary pull-right">添加</a>
        </#if>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>角色名称</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list roles as role>
            <tr>
              <td>${role.id}</td>
              <td>${role.name!}</td>
              <td>
                <#if sec.allGranted('role:edit')>
                  <a href="/admin/role/edit?id=${role.id}" class="btn btn-sm btn-warning">编辑</a>
                </#if>
                <#if sec.allGranted('role:delete')>
                  <a href="javascript:if(confirm('确定要删除吗？')) location.href='/admin/role/delete?id=${role.id}'" class="btn btn-sm btn-danger">删除</a>
                </#if>
              </td>
            </tr>
          </#list>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</@html>