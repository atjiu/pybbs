<#include "../../common/layout.ftl"/>
<@html page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="permission"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <@spring.message "site.panel.header.admin.permission.edit"/>
      </div>
      <div class="panel-body">
        <div class="panel-body">
          <form action="/admin/permission/${permission.id}/edit" method="post" id="permissionForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${permission.id!}"/>
            <div class="form-group">
              <label for="pid"><@spring.message "site.form.admin.permission.parentNode"/></label>
              <select name="pid" id="pid" class="form-control">
                <#list permissions as permission>
                  <option value="${permission.id!}">${permission.description!}</option>
                </#list>
              </select>
            </div>
            <div class="form-group">
              <label for="name"><@spring.message "site.form.admin.permission.name"/></label>
              <input type="text" id="name" name="name" value="${permission.name!}" class="form-control"
                     placeholder="<@spring.message "site.form.admin.permission.name.placeholder"/>: user:list"/>
            </div>
            <div class="form-group">
              <label for="url"><@spring.message "site.form.admin.permission.url"/></label>
              <input type="text" id="url" name="url" value="${permission.url!}" class="form-control"
                     placeholder="<@spring.message "site.form.admin.permission.url.placeholder"/>: /admin/user/list"/>
            </div>
            <div class="form-group">
              <label for="description"><@spring.message "site.form.admin.permission.introduce"/></label>
              <input type="text" id="description" name="description" value="${permission.description!}"
                     class="form-control" placeholder="<@spring.message "site.form.admin.permission.introduce.placeholder"/>"/>
            </div>
            <button type="button" id="permissionBtn" onclick="permissionSubmit()" class="btn btn-default btn-sm"><@spring.message "site.button.save"/>
            </button>
            <span id="error_message"></span>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
  $("#pid").val('${permission.pid}');
</script>
</@html>