<#include "layout/layout.ftl"/>
<@html page_title="登录" page_tab="login">
<div class="row">
  <div class="col-md-3 hidden-xs"></div>
  <div class="col-md-6">
    <div class="panel panel-info">
      <div class="panel-heading">登录</div>
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
            <label for="captcha">验证码</label>
            <div class="input-group">
              <input type="text" class="form-control" id="captcha" name="captcha" placeholder="验证码"/>
              <span class="input-group-btn">
                <img style="border: 1px solid #ccc;" src="/common/captcha" id="changeCaptcha"/>
              </span>
            </div>
          </div>
          <div class="form-group">
            <button type="button" id="login_btn" class="btn btn-info">登录</button>
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
    $("#changeCaptcha").click(function () {
      var date = new Date();
      $(this).attr("src", "/common/captcha?ver=" + date.getTime());
    })
    $("#login_btn").click(function () {
      var username = $("#username").val();
      var password = $("#password").val();
      var captcha = $("#captcha").val();
      if (!username) {
        toast("请输入用户名");
        return;
      }
      if (!password) {
        toast("请输入密码");
        return;
      }
      if (!captcha) {
        toast("请输入验证码");
        return;
      }
      $.ajax({
        url: '/api/login',
        type: 'post',
        cache: false,
        async: false,
        contentType: 'application/json',
        data: JSON.stringify({
          username: username,
          password: password,
          captcha: captcha,
        }),
        success: function(data) {
          if (data.code === 200) {
            toast("登录成功", "success");
            setTimeout(function () {
              window.location.href = "/";
            }, 700);
          } else {
            toast(data.description);
          }
        }
      })
    })
  })
</script>
</@html>
