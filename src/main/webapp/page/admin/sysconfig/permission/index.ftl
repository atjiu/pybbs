<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="permission">
<section class="content-header">
    <h1>
        设置
        <small>权限列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/permission">权限</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title">权限设置</h3>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-sm-3">
                    <div class="list-group">
                        <#list permissions as p>
                            <a href="javascript:;" permission-id="${p.id!}"
                               class="list-group-item <#if p.id == permissionId>active</#if>">
                            ${p.description!}
                            </a>
                        </#list>
                    </div>
                    <input type="button" class="btn btn-sm btn-default btn-block" id="addfPermission" value="添加父节点"
                           data-toggle="modal" data-target="#myModal">
                </div>
                <div class="col-sm-9">
                    <div class="dataTables_wrapper form-inline dt-bootstrap">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <th>名称</th>
                            <th>描述</th>
                            <th>操作</th>
                            </thead>
                            <#list permissions as p>
                                <tbody id="table_body_pid_${p.id!}" <#if p.id != permissionId>class="hidden"</#if>>
                                    <#list p.childPermission as permission>
                                    <tr id="tr_${permission.id!}">
                                        <td>${permission.name!}</td>
                                        <td>${permission.description!}</td>
                                        <td>
                                            <button onclick="editPermission('${permission.id!}', '${permission.pid!}')"
                                               data-toggle="modal" data-target="#myModal"
                                                class="btn btn-sm btn-primary">编辑</button>
                                            <a href="javascript:deletepermission('${permission.id!}', '${permission.pid!}')"
                                                class="btn btn-sm btn-danger">删除</a>
                                        </td>
                                    </tr>
                                    </#list>
                                </tbody>
                            </#list>
                        </table>
                        <input type="button" class="btn btn-sm btn-default btn-block" id="addPermission" value="添加权限"
                               data-toggle="modal" data-target="#myModal">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                </div>
                <div class="modal-body">
                    <form action="" class="form-horizontal" id="addPermissionForm">
                        <input type="hidden" id="id" value=""/>
                        <input type="hidden" id="pid" value="0"/>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">名称</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="name" name="name" placeholder="名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="description" name="description"
                                       placeholder="描述">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="addPermissionBtn">保存</button>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    var url, permissionId = '${permissionId!}';

    $(function () {
        $(".list-group-item").click(function () {
            permissionId = $(this).attr("permission-id");
            $(this).addClass("active");
            $(".list-group-item").each(function (i, v) {
                var clickele = $(v).attr("permission-id");
                if (permissionId != clickele) $(v).removeClass("active");
            });
            $("#table_body_pid_" + permissionId).removeClass("hidden");
            $("table tbody").each(function (i, v) {
                var body_id = $(v).attr("id");
                if (body_id != "table_body_pid_" + permissionId) {
                    $(v).addClass("hidden");
                }
            });
        });

        $("#addfPermission").click(function () {
            $("#myModalLabel").html("添加父节点");
            $("#pid").val(0);
            url = '${baseUrl!}/admin/permission/add';
        });

        $("#addPermission").click(function () {
            $("#myModalLabel").html("添加权限");
            $("#pid").val(permissionId);
            url = '${baseUrl!}/admin/permission/add';
        });

        $("#addPermissionForm").submit(function () {
            return false;
        });

        $("#addPermissionBtn").click(function () {
            var id = $("#id").val();
            var pid = $("#pid").val();
            var name = $("#name").val();
            var description = $("#description").val();
            console.log(url);
            $.ajax({
                url: url,
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id,
                    pid: pid,
                    name: name,
                    description: description
                },
                success: function (data) {
                    if (data.code == '200') {
                        location.href = '${baseUrl!}/admin/permission?permissionId=' + data.detail.pid;
                    } else {
                        alert(data.description);
                    }
                }
            });
        });

    });

    function editPermission(id, pid) {
        $("#myModalLabel").html("修改权限");
        var name = $("#tr_" + id).find("td").eq(0).text();
        var description = $("#tr_" + id).find("td").eq(1).text();
        $("#id").val(id);
        $("#pid").val(pid);
        $("#name").val(name);
        $("#description").val(description);
        url = '${baseUrl!}/admin/permission/edit';
    }

    function deletepermission(id, pid) {
        if (confirm("确定删除吗?")) {
            $.ajax({
                url: "${baseUrl!}/admin/permission/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id,
                    pid: pid
                },
                success: function (data) {
                    if (data.code == '200') {
                        location.href = '${baseUrl!}/admin/permission?permissionId=' + data.detail;
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