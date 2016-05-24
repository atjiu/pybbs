<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="label">
<link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/iCheck/square/blue.css">
<section class="content-header">
    <h1>
        设置
        <small>角色添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/role">角色</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">添加角色</h3>
        </div>
        <form class="form-horizontal" action="add" method="post">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="role.name" placeholder="名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="description" name="role.description" placeholder="标签描述">
                    </div>
                </div>
                <#list permissions as permission>
                    <div class="form-group">
                        <label for="" class="col-sm-2 control-label">${permission.description!}</label>
                        <div class="col-sm-10" style="padding-top: 4px;">
                            <#list permission.childPermission as p>
                                <input type="checkbox" name="permissions" id="permission_${p.id}" value="${p.id}">
                                <label for="permission_${p.id}">${p.description!}</label>
                            </#list>
                        </div>
                    </div>
                </#list>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>
<script src="${baseUrl!}/static/AdminLTE/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        $("input[type='checkbox']").iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</@layout>
