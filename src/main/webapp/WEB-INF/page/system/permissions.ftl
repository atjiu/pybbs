<#include "../common/layout.ftl"/>
<@html page_title="角色管理" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                权限管理
                <a class="pull-right" href="/manage/addpermission?pid=${pid!}">添加权限</a>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-responsive">
                    <tbody>
                        <#list childPermissions as permission>
                        <tr>
                            <td>${permission.id!}</td>
                            <td>${permission.name!}</td>
                            <td>${permission.url!}</td>
                            <td>${permission.description!}</td>
                            <td>
                                <a href="/manage/editpermission?id=${permission.id!}" class="btn btn-xs btn-warning">编辑</a>
                                <a href="javascript:if(confirm('确认删除吗?')) location.href='/manage/deletepermission?id=${permission.id!}'"
                                   class="btn btn-xs btn-danger">删除</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
        <div class="panel panel-default">
            <div class="panel-heading">父节点</div>
            <div class="list-group">
                <#list permissions as permission>
                    <li class="list-group-item permission-item <#if pid?? && pid == permission.id>active</#if>">
                        <a href="javascript:if(confirm('确认删除吗?'))location.href='/manage/deletepermission?id=${permission.id!}'">删除</a>
                        <a href="/manage/permissions?pid=${permission.id!}">
                            ${permission.description!}
                        </a>
                    </li>
                </#list>
            </div>
        </div>
    </div>
</div>
</@html>