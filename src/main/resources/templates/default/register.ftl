<#include "./common/layout.ftl">
<@html page_title="注册" page_tab="register">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 注册
      </div>
      <div class="panel-body">
        <#if errors??>
        <div class="alert alert-danger">${errors!}</div>
        </#if>
        <form role="form" action="/register" method="post" id="form" >
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
          </div>
          <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="密码">
          </div>
          <div class="form-group">
            <label for="email">邮箱</label>
              <div class="input-group">
              <input type="email" class="form-control" id="reg_email" placeholder="邮箱"/>
              <span class="input-group-btn">
                <button class="btn btn-raised btn-default" type="button" name="email" id="send_email_btn">发送邮件</button>
              </span>
            </div>
          </div>
          <div class="form-group">
            <label for="emailCode">邮箱验证码</label>
            <input type="text" class="form-control" id="emailCode" name="emailCode" placeholder="邮箱验证码">
          </div>
          <div class="form-group">
            <label for="email">验证码</label>
            <div class="input-group">
              <input type="text" class="form-control" id="code" name="code" placeholder="验证码"/>
              <span class="input-group-btn">
                <img src="/code" id="changeCode" />
              </span>
            </div>
          </div>
          <button type="submit" class="btn btn-default">注册</button>
          <span id="error_message"></span>
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
      var email = $("#email").val();
      var emailCode = $("#emailCode").val();
      var code = $("#code").val();
      if(username.length === 0) {
        $("#error_message").text("用户名不能为空");
        return false;
      }
      if(password.length === 0) {
        $("#error_message").text("密码不能为空");
        return false;
      }
      if(email.length === 0) {
        $("#error_message").text("邮箱不能为空")
      }
      if(emailCode.length === 0) {
        $("#error_message").text("邮箱验证码不能为空")
      }
      if(code.length === 0) {
        $("#error_message").text("验证码不能为空")
      }
    });
    $("#changeCode").click(function(){
      var date = new Date();
      $(this).attr("src", "/code?ver=" + date.getTime());
    });
    $("#send_email_btn").click(function () {
      $("#send_email_btn").attr("disabled", true);
      $.ajax({
        url: "/sendEmailCode",
        async: false,
        cache: false,
        type: 'get',
        dataType: "json",
        data: {
          type: 'reg',
          email: $("#reg_email").val()
        },
        success: function (data) {
          if (data.code === 200) {
            $("#send_email_btn").html("发送成功");
            $("#reg_email").attr("disabled", true);
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