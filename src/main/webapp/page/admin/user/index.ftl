<#include "/page/admin/common/_layout.ftl"/>
<@layout page_tab="user">
<section class="content-header">
    <h1>
        用户
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${baseUrl!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${baseUrl!}/admin/user"><i class="fa fa-tag"></i> 用户</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <form class="form-inline" method="get" action="${baseUrl!}/admin/user">
                <div class="form-group">
                    <input type="text" class="form-control" name="nickname" value="${nickname!}" placeholder="昵称"/>
                </div>
                <div class="form-group">
                    <input type="email" class="form-control" name="email" value="${email!}" placeholder="邮箱">
                </div>
                <button type="submit" class="btn btn-raised btn-default ">搜索</button>
            </form>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>头像</th>
                    <th>昵称</th>
                    <th>邮箱</th>
                    <th>QQ绑定</th>
                    <th>微博绑定</th>
                    <th>积分</th>
                    <th>注册时间</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as user>
                        <tr>
                            <td><a href="${baseUrl!}/user/${user.id}"><img src="${user.avatar!}" width="30"></a></td>
                            <td><a href="${baseUrl!}/user/${user.id}">${user.nickname!}</a></td>
                            <td><a href="mailto:${user.email!}">${user.email!}</a></td>
                            <td>
                                <#if user.qq_nickname?? && user.qq_nickname != "">
                                    <img src="${user.qq_avatar!}" width="30"/>${user.qq_nickname!}
                                </#if>
                            </td>
                        <td>
                            <#if user.sina_nickname?? && user.sina_nickname != "">
                                <img src="${user.sina_avatar!}" width="30"/>${user.sina_nickname!}</td>
                            </#if>
                            <td>${user.score!}</td>
                            <td>${user.in_time!}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">总用户数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/page/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/admin/user/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</@layout>
