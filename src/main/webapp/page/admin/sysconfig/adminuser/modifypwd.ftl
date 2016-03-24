<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="modifypwd">
<section class="content-header">
    <h1>
        设置
        <small>修改密码</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/adminuser">用户</a></li>
        <li class="active">修改密码</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">修改密码</h3>
        </div>
        <form class="form-horizontal" action="modifypwd" method="post">
            <input type="hidden" name="id" value="${adminUser.id!}"/>
            <div class="box-body">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="username" name="username" disabled="disabled" value="${adminUser.username!}" placeholder="用户名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">新密码</label>
                    <div class="col-sm-5">
                        <input type="password" class="form-control" id="password" name="password" value="" placeholder="新密码">
                    </div>
                </div>
                <div class="form-group">
                    <span class="text-red">${errMsg!}</span>
                </div>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>
</@layout>
