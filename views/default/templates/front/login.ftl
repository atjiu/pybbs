<#include "layout/" + layoutName/>
<@html page_title="登录" page_tab="login">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a data-pjax href="/">主页</a> / 登录
      </div>
      <div class="panel-body">
        <#if s?? && s == "reg">
          <div class="alert alert-success">注册成功，快快登录吧！</div>
        </#if>
        <form role="form" id="form">
          <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
          </div>
          <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="密码">
          </div>
          <div class="form-group">
            <label for="email">验证码</label>
            <div class="input-group">
              <input type="text" class="form-control" id="code" name="code" placeholder="验证码"/>
              <span class="input-group-btn">
                <img src="/common/code" id="changeCode"/>
              </span>
            </div>
          </div>
          <button type="submit" id="btn" class="btn btn-default">登录</button>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <div class="panel panel-default">
      <div class="panel-heading">社交帐号登录</div>
      <div class="panel-body">
        <a href="/oauth2/github/login" class="btn btn-success btn-block">Github登录</a>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(function () {
    $("#changeCode").click(function () {
      var date = new Date();
      $(this).attr("src", "/common/code?ver=" + date.getTime());
    })
    $("#form").submit(function() {
      var username = $("#username").val();
      var password = $("#password").val();
      var code = $("#code").val();
      if(!username) {
        toast('用户名不能为空');
        return false;
      }
      if(!password) {
        toast('密码不能为空');
        return false;
      }
      if(!code) {
        toast('验证码不能为空');
        return false;
      }
      $.ajax({
        url: '/api/login',
        type: 'post',
        async: false,
        cache: false,
        dataType: 'json',
        data: {
          username: $("#username").val(),
          password: $("#password").val(),
          code: $("#code").val()
        },
        success: function(data){
          if(data.code === 200) {
            toast("登录成功");
            window.location.href = "/";
          } else {
            toast(data.description);
          }
        }
      })
      return false;
    })
  })
</script>
</@html>
