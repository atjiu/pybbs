<#include "./common/layout.ftl">
<@html page_title="注册" page_tab="register">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 注册
      </div>
      <div class="panel-body">
        <form role="form" id="form" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" class="form-control" id="username" name="username"
                   placeholder="用户名,只能是2-16位的a-z,A-Z,0-9组合">
          </div>
          <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="密码">
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
            <div class="input-group">
              <input type="email" class="form-control" id="email" name="email" placeholder="邮箱"/>
              <span class="input-group-btn">
                <button class="btn btn-raised btn-default" type="button" id="send_email_btn">发送邮件</button>
              </span>
            </div>
          </div>
          <div class="form-group">
            <label for="emailCode">邮箱验证码</label>
            <input type="text" class="form-control" id="emailCode" name="emailCode" placeholder="邮箱验证码">
          </div>
          <div class="form-group">
            <label for="code">验证码</label>
            <div class="input-group">
              <input type="text" class="form-control" id="code" name="code" placeholder="验证码"/>
              <span class="input-group-btn">
                <img src="/common/code" id="changeCode"/>
              </span>
            </div>
          </div>
          <button type="submit" class="btn btn-default" id="reg_btn">注册</button>
          <span id="error_message"></span>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <div class="panel panel-default">
      <div class="panel-heading">社交帐号登录</div>
      <div class="panel-body">
        <a href="/github_login" class="btn btn-success btn-block">Github登录</a>
      </div>
    </div>
  </div>
</div>
<script>
  $(function () {
    $("#form").submit(function () {
      var username = $("#username").val();
      var password = $("#password").val();
      var email = $("#email").val();
      var emailCode = $("#emailCode").val();
      var code = $("#code").val();
      if (username.length === 0) {
        $("#error_message").text("用户名不能为空");
        return false;
      }
      if (password.length === 0) {
        $("#error_message").text("密码不能为空");
        return false;
      }
      if (email.length === 0) {
        $("#error_message").text("邮箱不能为空");
        return false;
      }
      if (emailCode.length === 0) {
        $("#error_message").text("邮箱验证码不能为空");
        return false;
      }
      if (code.length === 0) {
        $("#error_message").text("验证码不能为空");
        return false;
      }

      $("#error_message").text("");
      $.ajax({
        url: '/register',
        async: false,
        cache: false,
        type: "post",
        dataType: "json",
        data: {
          '${_csrf.parameterName}': '${_csrf.token}',
          username: $("#username").val(),
          password: $("#password").val(),
          email: $("#email").val(),
          emailCode: $("#emailCode").val(),
          code: $("#code").val()
        },
        success: function (data) {
          if (data.code === 200) {
            location.href = "/login?s=reg";
          } else {
            $("#error_message").text(data.description);
          }
        },
        error: function (err) {
          $("#error_message").text(err.message);
        }
      });

      return false;
    });

    $("#changeCode").click(function () {
      var date = new Date();
      $(this).attr("src", "/common/code?ver=" + date.getTime());
    });
    $("#send_email_btn").click(function () {
      $("#send_email_btn").attr("disabled", true);
      $.ajax({
        url: "/common/sendEmailCode",
        async: true,
        cache: false,
        type: 'get',
        dataType: "json",
        data: {
          type: 'reg',
          email: $("#email").val()
        },
        success: function (data) {
          if (data.code === 200) {
            $("#send_email_btn").html("发送成功");
            $("#email").attr("disabled", true);
          } else {
            $("#error_message").text(data.description);
            $("#send_email_btn").attr("disabled", false);
          }
        }
      });
    });
  })
</script>
</@html>