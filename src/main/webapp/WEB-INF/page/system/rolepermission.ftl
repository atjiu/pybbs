<#include "../common/layout.ftl"/>
<@html page_title="配置权限" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">配置权限</div>
            <div class="panel-body">
                <form action="/manage/rolepermission" method="post" id="roleForm">
                    <input type="hidden" name="id" value="${role.id!}"/>
                    <div class="form-group">
                        <label for="name">角色名称</label>
                        <input type="text" id="name" name="name" value="${role.name!}" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="description">角色描述</label>
                        <input type="text" id="description" name="description" value="${role.description!}" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="roles">权限</label>
                        <div>
                            <#list permissions as permission>
                                <h4><b>${permission.description!}</b></h4>
                                <#list permission.childPermissions as childPermission>
                                    <input type="checkbox" name="permissions" value="${childPermission.id!}" id="permission_${childPermission.id!}">
                                    <label for="permission_${childPermission.id!}">${childPermission.description!}</label>&nbsp;
                                </#list>
                            </#list>
                            <script type="text/javascript">
                                <#list _permissions as permission>
                                    $("#permission_${permission.pid!}").attr("checked", true);
                                </#list>
                            </script>
                        </div>
                    </div>
                    <button type="button" id="roleBtn" onclick="roleSubmit()" class="btn btn-sm btn-default">保存</button>
                    <span id="error_message"></span>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>