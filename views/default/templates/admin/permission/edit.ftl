<#include "../common/layout.ftl"/>
<@html page_title="编辑权限" page_tab="admin">
<div class="row">
  <div class="col-md-2 hidden-sm hidden-xs">
    <#include "../components/admin_left.ftl">
    <@admin_left page_tab="permission"/>
  </div>
  <div class="col-md-10">
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
            <button type="button" id="permissionBtn" onclick="permissionSubmit()"
                    class="btn btn-default btn-sm">保存
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

  function permissionSubmit() {
    var errors = 0;
    var em = $("#error_message");
    var pid = $("#pid").val();
    var name = $("#name").val();
    var url = $("#url").val();
    var description = $("#description").val();
    if (name.length === 0) {
      errors++;
      em.html("权限标识不能为空");
    }
    if (pid > 0 && url.length === 0) {
      errors++;
      em.html("授权路径不能为空");
    }
    if (description.length === 0) {
      errors++;
      em.html("权限描述不能为空");
    }
    if (errors === 0) {
      var form = $("#permissionForm");
      form.submit();
    }
  }
</script>
</@html>