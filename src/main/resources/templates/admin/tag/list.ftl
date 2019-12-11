<#include "../layout/layout.ftl">
<@html page_title="标签列表" page_tab="tag">
    <section class="content-header">
        <h1>
            标签
            <small>列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="/admin/tag/list">标签</a></li>
            <li class="active">列表</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">标签列表</h3>
                <#if sec.hasPermission('tag:async')>
                    <button type="button" onclick="asyncTopicCount()" class="btn btn-xs btn-danger pull-right">同步话题数
                    </button>
                    <script>
                        function asyncTopicCount() {
                            if (confirm('如果标签数比较多，这个操作会很耗费时间，确定继续吗？')) {
                                $.get("/admin/tag/async", function (data) {
                                    if (data.code === 200) {
                                        toast("成功", "success");
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 700);
                                    } else {
                                        toast(data.description);
                                    }
                                })
                            }
                        }
                    </script>
                </#if>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form action="/admin/tag/list" class="form-inline">
                    <div class="form-group" style="margin-bottom: 10px;">
                        <input type="text" name="name" value="${name!}" class="form-control" placeholder="标签名">
                        <button type="submit" class="btn btn-primary btn-sm">搜索</button>
                    </div>
                </form>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>图标</th>
                        <th>名称</th>
                        <th>话题数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list page.records as tag>
                        <tr>
                            <td>${tag.id}</td>
                            <td><img src="${tag.icon!}" width="30" alt=""></td>
                            <td><a href="/topic/tag/${tag.name!}" target="_blank">${tag.name!}</a></td>
                            <td>${tag.topicCount!0}</td>
                            <td>
                                <#if sec.hasPermission('tag:edit')>
                                    <a href="/admin/tag/edit?id=${tag.id}" class="btn btn-xs btn-warning">编辑</a>
                                </#if>
                                <#if sec.hasPermission('tag:delete')>
                                    <button onclick="deleteBtn('${tag.id}')" class="btn btn-xs btn-danger">删除</button>
                                </#if>
                            </td>
                        </tr>
                        <#if tag.intro??>
                            <tr>
                                <td colspan="5">${tag.description!}</td>
                            </tr>
                        </#if>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <#include "../layout/paginate.ftl">
        <@paginate currentPage=page.current totalPage=page.pages actionUrl="/admin/tag/list" urlParas="&name=${name!}"/>
    </section>
    <script>
        <#if sec.hasPermission('tag:delete')>

        function deleteBtn(id) {
            if (confirm('确定要删除这个标签吗？')) {
                $.get("/admin/tag/delete?id=" + id, function (data) {
                    if (data.code === 200) {
                        toast("成功", "success");
                        setTimeout(function () {
                            window.location.reload();
                        }, 700);
                    } else {
                        toast(data.description);
                    }
                })
            }
        }

        </#if>
    </script>
</@html>
