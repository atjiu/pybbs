<#include "../../common/layout.ftl"/>
<@html page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="role"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><@spring.message "site.panel.header.admin.role.add"/></div>
      <div class="panel-body">
        <form action="/admin/role/add" method="post" id="roleForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="name"><@spring.message "site.form.admin.role.name"/></label>
            <input type="text" id="name" name="name" class="form-control"/>
          </div>
          <div class="form-group">
            <label for="description"><@spring.message "site.form.admin.role.introduce"/></label>
            <input type="text" id="description" name="description" class="form-control"/>
          </div>
          <div class="form-group">
            <label for="roles"><@spring.message "site.form.admin.permissions"/></label>
            <div>
              <#list list as l>
                <h4><b>${l.permission.description!}</b></h4>
                <#list l.childPermissions as childPermission>
                  <input type="checkbox" name="permissionIds" value="${childPermission.id}"
                         id="permission_${childPermission.id}">
                  <label for="permission_${childPermission.id}">${childPermission.description!}</label>&nbsp;
                </#list>
              </#list>
            </div>
          </div>
          <button type="button" id="roleBtn" onclick="roleSubmit()" class="btn btn-sm btn-default"><@spring.message "site.button.save"/></button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
</div>
</@html>