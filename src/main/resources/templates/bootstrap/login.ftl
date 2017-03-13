<#include "./common/layout.ftl">
<@html page_title="登录" page_tab="login">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 登录
      </div>
      <div class="panel-body">
        <#if SPRING_SECURITY_LAST_EXCEPTION??>
          <div class="alert alert-danger">用户名或密码错误</div>
        </#if>
        <#if s?? && s == "reg">
          <div class="alert alert-success">注册成功，快快登录吧！</div>
        </#if>
        <form role="form" action="/login" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
          </div>
          <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="密码">
          </div>
          <button type="submit" class="btn btn-default">登录</button>
        </form>
      </div>
    </div>
  </div>
</div>
</@html>