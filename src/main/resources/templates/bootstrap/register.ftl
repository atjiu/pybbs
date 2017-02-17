<#include "./common/layout.ftl">
<@html page_tab="register">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/"><@spring.message "site.panel.header.home"/></a> / <@spring.message "site.panel.header.register"/>
      </div>
      <div class="panel-body">
        <#if errors??>
        <div class="alert alert-danger">${errors!}</div>
        </#if>
        <form role="form" action="/register" method="post" id="form">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username"><@spring.message "site.form.user.name"/></label>
            <input type="text" class="form-control" id="username" name="username" placeholder="<@spring.message "site.form.user.name"/>">
          </div>
          <div class="form-group">
            <label for="password"><@spring.message "site.form.user.password"/></label>
            <input type="password" class="form-control" id="password" name="password" placeholder="<@spring.message "site.form.user.password"/>">
          </div>
          <button type="submit" class="btn btn-default"><@spring.message "site.button.register"/></button>
        </form>
      </div>
    </div>
  </div>
</div>
<script>
  $(function () {
    $("#form").submit(function () {
      var username = $("#username").val();
      var password = $("#password").val();
      if(username.length == 0) {
        layui.msg("<@spring.message "site.prompt.text.usernameNotEmpty"/>", {icon: 2});
        return false;
      }
      if(password.length == 0) {
        layui.msg("<@spring.message "site.prompt.text.passwordNotEmpty"/>", {icon: 2});
        return false;
      }
    })
  })
</script>
</@html>