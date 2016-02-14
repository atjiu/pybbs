<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="topic">
<section class="content-header">
    <h1>
        话题
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/topic"><i class="fa fa-tag"></i> 话题</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered table-responsive">
                    <thead>
                    <th>标题</th>
                    <th>作者</th>
                    <th width="45">分类</th>
                    <th width="80">是否显示</th>
                    <th>时间</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as topic>
                        <tr id="topic_${topic.id!}">
                            <td>
                                <span id="topic_top_${topic.id!}">
                                    <#if topic.top == 1>
                                        <span class="label label-success">顶</span>
                                    </#if>
                                </span>
                                <span id="topic_good_${topic.id!}">
                                    <#if topic.good == 1>
                                        <span class="label label-primary">精</span>
                                    </#if>
                                </span>
                                <a href="/topic/${topic.id!}.html" target="_blank">${topic.title!}</a>
                            </td>
                            <td>${topic.nickname!}</td>
                            <td>${topic.sectionName!}</td>
                            <td>
                                <#if topic.show_status == 1>
                                    <input type="button" value="显示" id="topic_show_btn_${topic.id!}" onclick="setShowStatus('${topic.id!}')" class="btn btn-raised btn-default "/>
                                <#elseif topic.show_status == 0>
                                    <input type="button" value="不显示" id="topic_show_btn_${topic.id!}" onclick="setShowStatus('${topic.id!}')" class="btn btn-raised btn-warning "/>
                                </#if>
                            </td>
                            <td>${topic.in_time!}</td>
                            <td>
                                <a href="javascript:setTop('${topic.id!}')"><span class="glyphicon glyphicon-arrow-up" title="置顶"></span></a>
                                <a href="javascript:setGood('${topic.id!}')"><span class="glyphicon glyphicon-bookmark" title="精华"></span></a>
                                <a href="javascript:;" data-toggle="modal" data-target="#topic_detail_${topic.id!}">
                                    <span class="glyphicon glyphicon-eye-open" title="查看详情"></span>
                                </a>
                                <a href="${baseUrl!}/admin/topic/edit/${topic.id!}" target="_blank"><span class="glyphicon glyphicon-edit" title="编辑"></span></a>
                                <a href="javascript:deleteTopic('${topic.id}')"><span class="glyphicon glyphicon-trash" title="删除"></span></a>
                                <div class="modal fade" id="topic_detail_${topic.id!}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog modal-lg" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="myModalLabel">${topic.title!}</h4>
                                            </div>
                                            <div class="modal-body">
                                            ${topic.content!}
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-raised btn-default" data-dismiss="modal">关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">总话题数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/page/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/admin/topic/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</@layout>
<script type="text/javascript">
    function deleteTopic(id) {
        if(confirm("确定 删除话题 吗？\n(注：这会删除话题下的所有回复以及被别人收藏的记录！)")) {
            $.ajax({
                url : "${baseUrl!}/admin/topic/delete",
                async : false,
                cache : false,
                type : 'post',
                dataType : "json",
                data : {
                    id: id
                },
                success : function(data) {
                    if(data.code == '200') {
                        $("#topic_" + id).remove();
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
    function setTop(id) {
        if(confirm("确定 置顶/取消置顶 吗？")) {
            $.ajax({
                url : "${baseUrl!}/admin/topic/top",
                async : false,
                cache : false,
                type : 'post',
                dataType : "json",
                data : {
                    id: id
                },
                success : function(data) {
                    if(data.code == '200') {
                        if(data.detail.top == 1) {
                            $("#topic_top_" + id).html('<span class="label label-success">顶</span>');
                        } else if(data.detail.top == 0) {
                            $("#topic_top_" + id).html("");
                        }
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
    function setGood(id) {
        if(confirm("确定 加精/取消加精 吗？")) {
            $.ajax({
                url : "${baseUrl!}/admin/topic/good",
                async : false,
                cache : false,
                type : 'post',
                dataType : "json",
                data : {
                    id: id
                },
                success : function(data) {
                    if(data.code == '200') {
                        if(data.detail.good == 1) {
                            $("#topic_good_" + id).html('<span class="label label-primary">精</span>');
                        } else if(data.detail.good == 0) {
                            $("#topic_good_" + id).html("");
                        }
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
    function setShowStatus(id) {
        if(confirm("确定 更改显示状态 吗？")) {
            $.ajax({
                url : "${baseUrl!}/admin/topic/show_status",
                async : false,
                cache : false,
                type : 'post',
                dataType : "json",
                data : {
                    id: id
                },
                success : function(data) {
                    if(data.code == '200') {
                        if(data.detail.show_status == 1) {
                            $("#topic_show_btn_" + id).removeClass("btn-warning").addClass("btn-default").val("显示");
                        } else if(data.detail.show_status == 0) {
                            $("#topic_show_btn_" + id).removeClass("btn-default").addClass("btn-warning").val("不显示");
                        }
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
</script>
