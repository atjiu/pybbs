<#macro html page_title page_tab="">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${page_title} - ${site.name}</title>
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
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="/static/AdminLTE/dist/css/skins/_all-skins.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="/static/AdminLTE/plugins/iCheck/all.css">
  <!-- Morris chart -->
  <link rel="stylesheet" href="/static/AdminLTE/plugins/morris/morris.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="/static/AdminLTE/plugins/datepicker/datepicker3.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="/static/AdminLTE/plugins/daterangepicker/daterangepicker.css">
  <link rel="stylesheet" href="/static/AdminLTE/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="/static/AdminLTE/plugins/daterangepicker/daterangepicker.css">
  <link rel="stylesheet" href="/static/jquery-toast-plugin/jquery.toast.min.css">
  <link rel="stylesheet" href="/static/bootstrap-treeview/bootstrap-treeview.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="//oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <script src="/static/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
  <script>
    $.widget.bridge('uibutton', $.ui.button);
  </script>
  <script src="/static/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/raphael/2.1.0/raphael-min.js"></script>
  <script src="/static/AdminLTE/plugins/sparkline/jquery.sparkline.min.js"></script>
  <script src="/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
  <script src="/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
  <script src="/static/AdminLTE/plugins/knob/jquery.knob.js"></script>
  <script src="//cdn.bootcss.com/moment.js/2.20.1/moment.min.js"></script>
  <script src="/static/AdminLTE/plugins/daterangepicker/daterangepicker.js"></script>
  <script src="/static/AdminLTE/plugins/datepicker/bootstrap-datepicker.js"></script>
  <script src="/static/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
  <script src="/static/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
  <script src="/static/AdminLTE/plugins/fastclick/fastclick.js"></script>
  <script src="/static/AdminLTE/dist/js/app.min.js"></script>
  <script src="/static/AdminLTE/plugins/iCheck/icheck.min.js"></script>
  <script src="/static/jquery-toast-plugin/jquery.toast.min.js"></script>
  <script src="/static/bootstrap-treeview/bootstrap-treeview.min.js"></script>
  <script src="/static/AdminLTE/dist/js/demo.js"></script>

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
  </script>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<div>
  <#include "header.ftl">
  <#include "menu.ftl">
  <@menu page_tab=page_tab/>
  <div class="content-wrapper" style="padding: 50px 0; background-color: #fff;">
    <#nested>
  </div>
  <#include "footer.ftl"/>
</div>
</body>
</html>
</#macro>