<#include "../../common/layout.ftl"/>
<@html page_title="修改个人密码" page_tab="setting">
<div class="row">

  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../common/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="changePassword"/>
  </div>

  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        修改密码
      </div>
      <div class="panel-body">
        <form action="/user/changePassword" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="oldPassword">旧密码</label>
            <input type="password" class="form-control" id="oldPassword" name="oldPassword"/>
          </div>
          <div class="form-group">
            <label for="newPassword">新密码</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword"/>
          </div>
          <#if sec.isLock()>
            <button type="button" class="btn btn-default" disabled="disabled" data-toggle="tooltip"
                    data-placement="bottom" title="你的帐户已经被禁用了">修改密码
            </button>
          <#else>
            <button type="submit" class="btn btn-default">修改密码</button>
          </#if>
          <span class="text-danger">${changePasswordErrorMsg!}</span>
        </form>
      </div>
    </div>
  </div>
</div>
<script>
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })
</script>
</@html>