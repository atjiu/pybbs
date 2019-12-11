<#include "../layout/layout.ftl">
<@html page_title="角色添加" page_tab="auth_role">
    <section class="content-header">
        <h1>
            角色
            <small>添加</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="/admin/role/list">角色</a></li>
            <li class="active">添加</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">角色添加</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form id="form" action="/admin/role/add" method="post">
                    <div class="form-group">
                        <label>角色名</label>
                        <input type="text" name="name" class="form-control" placeholder="角色名">
                    </div>
                    <div class="form-group">
                        <#list permissions?keys as key>
                            <label for="">${key}</label>
                            <p>
                                <#list permissions[key] as permission>
                                    <input type="checkbox" name="permissionIds" id="permission_${permission.id}"
                                           value="${permission.id}"/>
                                    <label for="permission_${permission.id}">${permission.name}</label>
                                </#list>
                            </p>
                        </#list>
                    </div>
                    <button type="submit" class="btn btn-xs btn-primary">保存</button>
                </form>
            </div>
        </div>
    </section>
</@html>
