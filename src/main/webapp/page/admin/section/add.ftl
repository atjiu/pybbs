<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="section">
<section class="content-header">
    <h1>
        板块
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/section"><i class="fa fa-tag"></i> 板块</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建板块</h3>
        </div>
        <form class="form-horizontal" action="add" method="post">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="name" placeholder="名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="show_status" class="col-sm-2 control-label">是否显示</label>
                    <div class="col-sm-5">
                        <select name="show_status" id="show_status" class="form-control">
                            <option value="1">显示</option>
                            <option value="0">不显示</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tab" class="col-sm-2 control-label">标识</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="tab" name="tab" placeholder="标识">
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