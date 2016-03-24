<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="role">
<section class="content-header">
    <h1>
        设置
        <small>角色列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/role">角色</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <a href="${baseUrl!}/admin/role/add" class="btn btn-raised  btn-default pull-right">添加</a>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>名称</th>
                    <th>描述</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as role>
                        <tr id="role_${role.id!}">
                            <td>${role.name!}</td>
                            <td>${role.description!}</td>
                            <td>
                                <a href="${baseUrl!}/admin/role/edit?id=${role.id!}" class="btn btn-sm btn-primary">编辑</a>
                                <a href="javascript:deleteRole('${role.id!}')" class="btn btn-sm btn-danger">删除</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">总条数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/page/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/admin/role" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    function deleteRole(id) {
        if (confirm("确定删除吗?")) {
            $.ajax({
                url: "${baseUrl!}/admin/role/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("#role_" + id).remove();
                    } else {
                        alert(data.description);
                    }
                },
                error: function(xhr, type) {
                    alert("删除失败");
                }
            });
        }
    }
</script>
</@layout>