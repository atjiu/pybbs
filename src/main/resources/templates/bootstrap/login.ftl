<#include "./common/layout.ftl">
<@html page_tab="login">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/"><@spring.message "site.panel.header.home"/></a> / <@spring.message "site.panel.header.login"/>
      </div>
      <div class="panel-body">
        <#if SPRING_SECURITY_LAST_EXCEPTION??>
          <div class="alert alert-danger"><@spring.message "site.prompt.text.user.usernameOrPasswordIncorrect"/></div>
        </#if>
        <#if s?? && s == "reg">
          <div class="alert alert-success"><@spring.message "site.prompt.text.user.signUpSuccess"/></div>
        </#if>
        <form role="form" action="/login" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username"><@spring.message "site.form.user.name"/></label>
            <input type="text" class="form-control" id="username" name="username" placeholder="<@spring.message "site.form.user.name"/>">
          </div>
          <div class="form-group">
            <label for="password"><@spring.message "site.form.user.password"/></label>
            <input type="password" class="form-control" id="password" name="password" placeholder="<@spring.message "site.form.user.password"/>">
          </div>
          <div class="checkbox">
            <label>
              <input type="checkbox" name="remember-me"> <@spring.message "site.form.user.rememberMe"/>
            </label>
          </div>
          <button type="submit" class="btn btn-default"><@spring.message "site.button.login"/></button>
        </form>
      </div>
    </div>
  </div>
</div>
</@html>