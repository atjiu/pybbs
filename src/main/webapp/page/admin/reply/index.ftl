<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="reply">
<section class="content-header">
    <h1>
        回复
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/reply"><i class="fa fa-tag"></i> 回复</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>标题</th>
                    <th>作者</th>
                    <th width="70">内容</th>
                    <th width="150">时间</th>
                    <th width="45">操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as reply>
                        <tr>
                            <td><a href="${baseUrl!}/topic/${reply.tid!}.html#${reply.id!}" target="_blank">${reply.title!}</a></td>
                            <td>${reply.nickname}</td>
                            <td>
                                <#if reply.isdelete == 1>
                                    <input type="button" value="已删除" id="reply_input_${reply.id!}" data-toggle="modal" data-target="#reply_${reply.id!}" class="btn btn-raised btn-danger "/>
                                <#else>
                                    <input type="button" value="查看" id="reply_input_${reply.id!}" data-toggle="modal" data-target="#reply_${reply.id!}" class="btn btn-raised btn-default "/>
                                </#if>
                                <div class="modal fade" id="reply_${reply.id!}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="myModalLabel">${reply.title!}</h4>
                                            </div>
                                            <div class="modal-body">
                                            ${reply.content!}
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-raised btn-default" data-dismiss="modal">关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td>${reply.in_time!}</td>
                            <td>
                                <a href="javascript:deleteReply('${reply.id!}')"><span class="glyphicon glyphicon-trash"></span></a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">总回复数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/page/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/admin/reply/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</@layout>
<script type="text/javascript">
    function deleteReply(id) {
        if(confirm("确定 删除回复 吗？)")) {
            $.ajax({
                url : "${baseUrl!}/admin/reply/delete",
                async : false,
                cache : false,
                type : 'post',
                dataType : "json",
                data : {
                    id: id
                },
                success : function(data) {
                    if(data.code == '200') {
                        $("#reply_input_" + id).removeClass("btn-default").addClass("btn-danger").val("已删除");
                        $("#reply_" + id + " .modal-body").html("回复已被删除");
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
</script>
