<#include "../../common/layout.ftl"/>
<@html>
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="user"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><@spring.message "site.panel.header.admin.user.edit"/></div>
      <div class="panel-body">
        <form action="/admin/user/${user.id}/role" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="nickname"><@spring.message "site.form.user.name"/></label>
            <input type="text" disabled id="username" value="${user.username}" class="form-control"/>
          </div>
          <div class="form-group">
            <label for="roles"><@spring.message "site.form.admin.roles"/></label>
            <div>
              <#list roles as role>
                <input type="checkbox" name="roleIds" value="${role.id}" id="role_${role.id}">
                <label for="role_${role.id}">${role.description}</label>&nbsp;
              </#list>
              <script type="text/javascript">
                  <#list user.roles as role>
                  $("#role_${role.id}").attr("checked", true);
                  </#list>
              </script>
            </div>
          </div>
          <button type="submit" class="btn btn-sm btn-default"><@spring.message "site.button.save"/></button>
        </form>
      </div>
    </div>
  </div>
</div>
</@html>