<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="label">
<section class="content-header">
    <h1>
        标签
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/label"><i class="fa fa-tag"></i> 标签</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建友链</h3>
        </div>
        <form class="form-horizontal" action="add" method="post" enctype="multipart/form-data">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="name" placeholder="名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="description" name="description" placeholder="标签描述">
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">图片</label>
                    <div class="col-sm-6">
                        <input type="file" class="form-control" id="img" name="img" placeholder="标签图片">
                        <p class="text-red">请上传73x73大小的图片</p>
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
