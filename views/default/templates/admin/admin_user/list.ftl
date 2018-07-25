<#include "../layout/layout.ftl">
<@html page_title="用户列表" page_tab="admin_user_list">
  <section class="content-header">
    <h1>
     用户
      <small>列表</small>
    </h1>
    <ol class="breadcrumb">
      <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
      <li><a href="/admin/user/list">用户</a></li>
      <li class="active">列表</li>
    </ol>
  </section>
  <section class="content">
    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">用户列表</h3>
        <#if sec.allGranted('admin_user:add')>
          <a href="/admin/admin_user/add" class="btn btn-sm btn-primary pull-right">添加</a>
        </#if>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>#</th>
            <th>用户名</th>
            <th>注册时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <#list page.content as user>
            <tr>
              <td>${user.id}</td>
              <td>${user.username!}</td>
              <td>${user.inTime?datetime}</td>
              <td>
                <#if sec.allGranted('admin_user:edit')>
                  <a href="/admin/admin_user/edit?id=${user.id}" class="btn btn-sm btn-warning">编辑</a>
                </#if>
                <#if sec.allGranted('admin_user:delete')>
                  <a href="javascript:if(confirm('确定要删除吗？')) location.href='/admin/admin_user/delete?id=${user.id}'" class="btn btn-sm btn-danger">删除</a>
                </#if>
              </td>
            </tr>
          </#list>
          </tbody>
        </table>
      </div>
      <#if page.totalPages &gt; 1>
        <div class="box-footer clearfix">
          <#include "../layout/paginate.ftl">
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/admin/user/list" urlParas=""/>
        </div>
      </#if>
    </div>
  </section>
</@html>