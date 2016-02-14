<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="link">
<section class="content-header">
    <h1>
        友链
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/link"><i class="fa fa-tag"></i> 友链</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <a href="${baseUrl!}/admin/link/add" class="btn btn-raised  btn-default pull-right">添加</a>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <form id="form-sort" action="${baseUrl!}/admin/link/sort" method="post">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <th>名称</th>
                        <th>访问地址</th>
                        <th>操作</th>
                        </thead>
                        <tbody id="sortable">
                            <#list admin_links as link>
                            <tr class="ui-state-default" id="link_${link.id!}">
                                <td>
                                    <input type="hidden" name="ids" value="${link.id!}">
                                ${link.name!}
                                </td>
                                <td><a href="${link.url!}" target="_blank">${link.url!}</a></td>
                                <td>
                                    <a href="${baseUrl!}/admin/link/edit?id=${link.id!}"><span class="glyphicon glyphicon-edit"></span></a>
                                    <a href="javascript:deleteLink('${link.id!}')"><span class="glyphicon glyphicon-trash"></span></a>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                    <input type="submit" class="btn btn-raised btn-default " value="点击保存排序">
                </form>
            </div>
        </div>
    </div>
</section>
<script>
    $(function() {
        $( "#sortable" ).sortable();
        $( "#sortable" ).disableSelection();
    });

    function deleteLink(id) {
        if(confirm("确定 删除友链 吗？")) {
            $.ajax({
                url : "${baseUrl!}/admin/link/delete",
                async : false,
                cache : false,
                type : 'post',
                dataType : "json",
                data : {
                    id: id
                },
                success : function(data) {
                    if(data.code == '200') {
                        $("#link_" + id).remove();
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
</script>
</@layout>