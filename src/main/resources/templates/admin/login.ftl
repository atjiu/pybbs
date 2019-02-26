<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>后台登录</title>
    <#--css-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.4.8/css/AdminLTE.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.4.8/css/skins/_all-skins.min.css"/>

    <#--javascript-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/admin-lte/2.4.8/js/adminlte.min.js"></script>

    <script>
      function toast(txt, icon) {
        $.toast({
          text: txt, // Text that is to be shown in the toast
          heading: '系统提醒', // Optional heading to be shown on the toast
          icon: icon || 'error', // Type of toast icon warning, info, success, error
          showHideTransition: 'slide', // fade, slide or plain
          allowToastClose: true, // Boolean value true or false
          hideAfter: 2000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
          stack: false, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
          position: 'top-right', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
        });
      }
    </script>
  </head>
  <body class="hold-transition login-page">
  <div class="login-box">
    <div class="login-logo">
      <a href="javascript:;"><b>ADMIN</b>${site.name}</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
      <p class="login-box-msg">${site.name} 管理平台登录</p>
      <#if error??>
        <div class="text-red">${error}</div>
      </#if>
      <form id="form" action="/adminlogin" method="post">
        <div class="form-group has-feedback">
          <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
          <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
          <input type="password" class="form-control" id="password" name="password" placeholder="密码">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group">
          <div class="input-group">
            <input type="text" class="form-control" id="code" name="code" placeholder="验证码"/>
            <span class="input-group-btn">
            <img style="border: 1px solid #ccc;" src="/common/captcha" id="changeCode"/>
          </span>
          </div>
        </div>
        <div class="row">
          <div class="col-xs-8">
            <input type="checkbox" name="rememberMe" checked id="rememberMe" value="1"> <label for="rememberMe">记住我</label>
          </div>
          <!-- /.col -->
          <div class="col-xs-4">
            <button type="submit" class="btn btn-primary btn-block btn-flat"><i class="fa fa-adminUser"></i> 登录</button>
          </div>
          <!-- /.col -->
        </div>
      </form>
    </div>
    <!-- /.login-box-body -->
  </div>
  <script>
    $(function() {
      $("#changeCode").click(function () {
        var date = new Date();
        $(this).attr("src", "/common/captcha?ver=" + date.getTime());
      })
    });
  </script>
  </body>
</html>
