<#include "../common/layout.ftl"/>
<@html page_title="角色管理" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                角色管理
                <a href="/manage/addrole" class="pull-right">添加角色</a>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-responsive">
                    <tbody>
                    <#list roles as role>
                        <tr>
                            <td>${role.id!}</td>
                            <td>${role.name!}</td>
                            <td>${role.description!}</td>
                            <td>
                                <a href="/manage/rolepermission?id=${role.id!}" class="btn btn-xs btn-warning">配置权限</a>
                                <a href="javascript:if(confirm('确认删除吗?')) location.href='/manage/deleterole?id=${role.id!}'" class="btn btn-xs btn-danger">删除</a>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>