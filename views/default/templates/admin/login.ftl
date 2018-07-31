<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>登录</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="/static/AdminLTE/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
  <!-- Ionicons -->
  <link href="//cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet">
  <!-- Theme style -->
  <link rel="stylesheet" href="/static/AdminLTE/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="/static/AdminLTE/plugins/iCheck/square/blue.css">
  <link rel="stylesheet" href="/static/jquery-toast-plugin/jquery.toast.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="//oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="javascript:;"><b>ADMIN</b>${site.name!}</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">${site.name!} 管理平台登录</p>
    <#if SPRING_SECURITY_LAST_EXCEPTION??>
      <div class="text-red">${(SPRING_SECURITY_LAST_EXCEPTION.message)!}</div>
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
            <img src="/common/code" id="changeCode"/>
          </span>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <input type="checkbox" name="remember-me" id="remember-me" checked> <label for="remember-me">记住我</label>
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
<!-- /.login-box -->
<!-- jQuery 2.2.3 -->
<script src="/static/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/static/AdminLTE/plugins/iCheck/icheck.min.js"></script>
<script src="/static/jquery-toast-plugin/jquery.toast.min.js"></script>
<script>
  function toast(txt, icon) {
    $.toast({
      text: txt, // Text that is to be shown in the toast
      heading: '系统提醒', // Optional heading to be shown on the toast
      icon: icon || 'warning', // Type of toast icon warning, info, success, error
      showHideTransition: 'plain', // fade, slide or plain
      allowToastClose: true, // Boolean value true or false
      hideAfter: 2000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
      stack: false, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
      position: 'top-right', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
    });
  }
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    $("#changeCode").click(function () {
      var date = new Date();
      $(this).attr("src", "/common/code?ver=" + date.getTime());
    })
    $("#form").submit(function() {
      var username = $("#username").val();
      var password = $("#password").val();
      var code = $("#code").val();
      if(!username) {
        toast("用户名不能为空");
        return false;
      }
      if(!password) {
        toast("密码不能为空");
        return false;
      }
      if(!code) {
        toast("验证码不能为空");
        return false;
      }
      return true;
    })
  });
</script>
</body>
</html>
