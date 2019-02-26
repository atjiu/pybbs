<#include "layout/layout.ftl"/>
<@html page_title="注册" page_tab="register">
  <div class="box" style="width: 500px;">
    <div class="box-heading">注册</div>
    <div class="box-body">
      <table style="width: 100%;border-spacing: 5px;">
        <tr>
          <td width="60">用户名</td>
          <td colspan="2"><input type="text" name="username" id="username" placeholder="用户名"/></td>
        </tr>
        <tr>
          <td>密码</td>
          <td colspan="2"><input type="password" name="password" id="password" placeholder="密码"/></td>
        </tr>
        <tr>
          <td>验证码</td>
          <td><input type="text" name="captcha" id="captcha" placeholder="验证码"/></td>
          <td width="122" align="right"><img src="/common/captcha" id="captchaImage" onclick="changeCaptcha()"/></td>
        </tr>
      </table>
    </div>
    <div class="box-footer">
      <a href="/login">登录</a>
      <button class="pull-right" onclick="register()">注册</button>
    </div>
  </div>
  <script>
    function changeCaptcha() {
      var date = new Date();
      $("#captchaImage").attr("src", "/common/captcha?ver=" + date.getTime());
    }
    function register() {
      var username = $("#username").val();
      var password = $("#password").val();
      var captcha = $("#captcha").val();
      if (!username) {
        alert("请输入用户名");
        return;
      }
      if (!password) {
        alert("请输入密码");
        return;
      }
      if (!captcha) {
        alert("请输入验证码");
        return;
      }
      $(".loading").show();
      $.ajax({
        url: '/api/register',
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
            window.location.href = "/";
          } else {
            alert(data.description);
          }
          $(".loading").hide();
        }
      })
    }
  </script>
</@html>
