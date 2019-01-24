<#include "./layout/layout.ftl"/>
<@html page_title="注册" page_tab="register">
<div class="row">
  <div class="col-md-3 hidden-xs"></div>
  <div class="col-md-6">
    <div class="panel panel-info">
      <div class="panel-heading">注册</div>
      <div class="panel-body">
        <form action="" onsubmit="return;" id="form">
          <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="用户名"/>
          </div>
          <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码"/>
          </div>
          <div class="form-group">
            <button type="button" id="register_btn" class="btn btn-info">注册</button>
            <#if !model.isEmpty(site.oauth_github_client_id!) && !model.isEmpty(site.oauth_github_client_secret!)>
              <a href="/oauth/github" class="btn btn-primary pull-right">Github登录</a>
            </#if>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-xs"></div>
</div>
<script>
  $(function () {
    $("#register_btn").click(function() {
      var username = $("#username").val();
      var password = $("#password").val();
      if (!username) {
        toast("请输入用户名");
        return;
      }
      if (!password) {
        toast("请输入密码");
        return;
      }
      $.post("/api/register", {
        username: username,
        password: password
      }, function (data) {
        if (data.code === 200) {
          toast("注册成功", "success");
          setTimeout(function () {
            window.location.href = "/";
          }, 700);
        } else {
          toast(data.description);
        }
      })
    })
  })
</script>
</@html>
