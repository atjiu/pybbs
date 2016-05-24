<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="adminuser">
<section class="content-header">
    <h1>
        设置
        <small>用户列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/adminuser">用户</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <a href="${baseUrl!}/admin/adminuser/add" class="btn btn-raised  btn-default pull-right">添加</a>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>用户名</th>
                    <th>注册时间</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as adminuser>
                        <tr id="adminuser_${adminuser.id!}">
                            <td>${adminuser.username!}</td>
                            <td>${adminuser.in_time!}</td>
                            <td>
                                <a href="${baseUrl!}/admin/adminuser/edit?id=${adminuser.id!}" class="btn btn-sm btn-primary">编辑</a>
                                <a href="javascript:deleteadminuser('${adminuser.id!}')" class="btn btn-sm btn-danger">删除</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" adminuser="status" aria-live="polite">总条数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/page/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/admin/adminuser" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
function deleteadminuser(id) {
    if (confirm("确定删除吗?")) {
        $.ajax({
            url: "${baseUrl!}/admin/adminuser/delete",
            async: false,
            cache: false,
            type: 'post',
            dataType: "json",
            data: {
                id: id
            },
            success: function (data) {
                if (data.code == '200') {
                    $("#adminuser_" + id).remove();
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