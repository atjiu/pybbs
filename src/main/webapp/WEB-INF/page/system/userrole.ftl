<#include "../common/layout.ftl"/>
<@html page_title="配置角色" page_tab="setting">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">配置角色</div>
            <div class="panel-body">
                <form action="/manage/userrole" method="post">
                    <input type="hidden" name="id" value="${user.id!}"/>
                    <div class="form-group">
                        <label for="nickname">昵称</label>
                        <input type="text" disabled id="nickname" value="${user.nickname!}" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label for="roles">角色</label>
                        <div>
                            <#list roles as role>
                                <input type="checkbox" name="roles" value="${role.id!}" id="role_${role.id!}">
                                <label for="role_${role.id!}">${role.description!}</label>&nbsp;
                            </#list>
                            <script type="text/javascript">
                                <#list _roles as role>
                                    $("#role_${role.rid!}").attr("checked", true);
                                </#list>
                            </script>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-sm btn-default">保存</button>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>