<#include "../common/layout.ftl"/>
<@html page_title="用户管理" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                用户管理
                <span class="pull-right">${page.getTotalRow()!}个用户</span>
            </div>
            <div class="table-responsive">
                <table class="table table-striped">
                    <tbody>
                    <#list page.getList() as user>
                        <tr>
                            <td>${user.id!}</td>
                            <td>${user.nickname!}</td>
                            <td>${user.email!}</td>
                            <td>${user.url!}</td>
                            <td>
                                <a href="/manage/userrole?id=${user.id!}" class="btn btn-xs btn-warning">配置角色</a>
                                <a href="javascript:if(confirm('确认删除吗?')) location.href='/manage/deleteuser?id=${user.id!}'" class="btn btn-xs btn-danger">删除</a>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="panel-body" style="padding: 0 15px;">
                <#include "../components/paginate.ftl"/>
                    <@paginate currentPage=page.getPageNumber() totalPage=page.getTotalPage() actionUrl="/manage/users" urlParas="" showdivide="no"/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>