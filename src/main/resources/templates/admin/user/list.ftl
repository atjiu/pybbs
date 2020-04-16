<#include "../layout/layout.ftl"/>
<@html page_title="用户" page_tab="user">
    <section class="content-header">
        <h1>
            用户
            <small>列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="/admin/user/list">用户</a></li>
            <li class="active">列表</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-info">
            <div class="box-header with-border">
                <h3 class="box-title">用户列表</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <form action="/admin/user/list" class="form-inline">
                    <div class="form-group" style="margin-bottom: 10px;">
                        <input type="text" id="username" name="username" value="${username!}" class="form-control"
                               placeholder="用户名">
                        <select type="text" id="orderBy" name="orderBy" value="${orderBy!}" class="form-control"
                                placeholder="排序方式">
                            <option value="">排序方式</option>
                            <option value="score">积分</option>
                            <option value="in_time">时间</option>
                        </select>
                        <select type="text" id="order" name="order" value="${order!}" class="form-control"
                                placeholder="顺序">
                            <option value="">降/升序</option>
                            <option value="desc">降序</option>
                            <option value="asc">升序</option>
                        </select>
                        <button type="submit" class="btn btn-primary btn-sm">搜索</button>
                    </div>
                </form>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>用户名</th>
                        <th>邮箱</th>
                        <th>积分</th>
                        <th>时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list page.records as user>
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username!}</td>
                            <td>${user.email!}</td>
                            <td>${user.score!0}</td>
                            <td>${user.inTime?datetime}</td>
                            <td>
                                <#if sec.hasPermission("user:edit")>
                                    <a href="/admin/user/edit?id=${user.id}" class="btn btn-xs btn-warning">编辑</a>
                                </#if>
                                <#if sec.hasPermission("user:delete")>
                                    <button onclick="deleteUser('${user.id}')" class="btn btn-xs btn-danger">删除</button>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <#include "../layout/paginate.ftl">
        <@paginate currentPage=page.current totalPage=page.pages actionUrl="/admin/user/list" urlParas="&orderBy=${orderBy!}&order=${order!}"/>
    </section>
    <script>
        $(function(){
            $("#orderBy").val("${orderBy!}");
            $("#order").val("${order!}");
        });

        <#if sec.hasPermission("user:delete")>

        function deleteUser(id) {
            if (confirm("确定要删除这个用户吗？\n 删除用户后，它发的帖子评论以及收藏就都没了，还请三思!!")) {
                $.get("/admin/user/delete?id=" + id, function (data) {
                    if (data.code === 200) {
                        toast("删除成功", "success");
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
