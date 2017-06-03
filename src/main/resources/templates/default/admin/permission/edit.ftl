<#include "../../common/layout.ftl"/>
<@html page_title="编辑权限" page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="permission"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        编辑权限
      </div>
      <div class="panel-body">
        <div class="panel-body">
          <form action="/admin/permission/${permission.id}/edit" method="post" id="permissionForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${permission.id!}"/>
            <div class="form-group">
              <label for="pid">父节点</label>
              <select name="pid" id="pid" class="form-control">
                <#list permissions as permission>
                  <option value="${permission.id!}">${permission.description!}</option>
                </#list>
              </select>
            </div>
            <div class="form-group">
              <label for="name">权限标识</label>
              <input type="text" id="name" name="name" value="${permission.name!}" class="form-control"
                     placeholder="权限标识，如：user:list"/>
            </div>
            <div class="form-group">
              <label for="url">授权地址</label>
              <input type="text" id="url" name="url" value="${permission.url!}" class="form-control"
                     placeholder="授权地址，如：/admin/user/list"/>
            </div>
            <div class="form-group">
              <label for="description">权限描述</label>
              <input type="text" id="description" name="description" value="${permission.description!}"
                     class="form-control" placeholder="权限描述，如：用户列表"/>
            </div>
            <button type="button" id="permissionBtn" onclick="permissionSubmit()" class="btn btn-default btn-sm">保存
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