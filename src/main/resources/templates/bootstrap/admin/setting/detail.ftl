<#include "../../common/layout.ftl">
<@html>
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="setting"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading"><@spring.message "site.panel.header.admin.setting.detail"/></div>
      <div class="panel-body">
        <form role="form" action="/admin/setting/update" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="theme"><@spring.message "site.form.admin.setting.theme"/></label>
            <input type="text" class="form-control" name="theme" id="theme" value="${themeSetting.value!}" />
          </div>
          <div class="form-group">
            <label for="pageSize"><@spring.message "site.form.admin.setting.pageSize"/></label>
            <input type="text" class="form-control" name="pageSize" id="pageSize" value="${pageSizeSetting.value!}" />
          </div>
          <div class="form-group">
            <label for="baseUrl"><@spring.message "site.form.admin.setting.baseUrl"/></label>
            <input type="text" class="form-control" name="baseUrl" id="baseUrl" value="${baseUrlSetting.value!}" />
          </div>
          <div class="form-group">
            <label for="editor"><@spring.message "site.form.admin.setting.editor"/></label>
            <input type="text" class="form-control" name="editor" id="editor" value="${editorSetting.value!}" />
          </div>
          <div class="form-group">
            <label for="uploadPath"><@spring.message "site.form.admin.setting.uploadPath"/></label>
            <input type="text" class="form-control" name="uploadPath" id="uploadPath" value="${uploadPathSetting.value!}" />
          </div>
          <div class="form-group">
            <label for="avatarPath"><@spring.message "site.form.admin.setting.avatarPath"/></label>
            <input type="text" class="form-control" name="avatarPath" id="avatarPath" value="${avatarPathSetting.value!}" />
          </div>
          <div class="form-group">
            <label for="search"><@spring.message "site.form.admin.setting.search"/></label>
            <input type="text" class="form-control" name="search" id="search" value="${searchSetting.value!}" />
          </div>
          <button type="submit" class="btn btn-default"><@spring.message "site.button.save"/></button>
        </form>
      </div>
    </div>
  </div>
</div>
</@html>