<#include "../layout/layout.ftl">
<@html page_title="用户列表" page_tab="auth_admin_user">
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
                <#if sec.hasPermission('admin_user:add')>
                    <a href="/admin/admin_user/add" class="btn btn-xs btn-primary pull-right">添加</a>
                </#if>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>用户名</th>
                        <th>角色</th>
                        <th>注册时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list adminUsers as adminUser>
                        <tr>
                            <td>${adminUser.id}</td>
                            <td>${adminUser.username!}</td>
                            <td>${adminUser.roleName!}</td>
                            <td>${adminUser.inTime?datetime}</td>
                            <td>
                                <#if sec.hasPermission('admin_user:edit')>
                                    <a href="/admin/admin_user/edit?id=${adminUser.id}" class="btn btn-xs btn-warning">编辑</a>
                                </#if>
                                <#if sec.hasPermission('admin_user:delete')>
                                    <a href="javascript:if(confirm('确定要删除吗？')) location.href='/admin/admin_user/delete?id=${adminUser.id}'"
                                       class="btn btn-xs btn-danger">删除</a>
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
