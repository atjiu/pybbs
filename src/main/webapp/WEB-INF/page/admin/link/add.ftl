<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="link">
<section class="content-header">
    <h1>
        友链
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/link"><i class="fa fa-tag"></i> 友链</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建友链</h3>
        </div>
        <form class="form-horizontal" action="add" method="post">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="link.name" placeholder="名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-2 control-label">访问地址</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="url" name="link.url" placeholder="访问地址">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>
</@layout>