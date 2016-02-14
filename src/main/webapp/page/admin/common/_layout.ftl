
<#macro layout page_tab="">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>后台管理 - ${siteTitle!}</title>
    <link rel="icon" href="${baseUrl!}/static/favicon.ico">

    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/bootstrap/css/bootstrap.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/datatables/dataTables.bootstrap.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${baseUrl!}/static/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${baseUrl!}/static/ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/dist/css/skins/_all-skins.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/iCheck/flat/blue.css">
    <!-- Morris chart -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/morris/morris.css">
    <!-- jvectormap -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/datepicker/datepicker3.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/daterangepicker/daterangepicker-bs3.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="${baseUrl!}/static/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

    <link rel="stylesheet" href="${baseUrl!}/static/css/admin.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery 2.2.0 -->
    <script src="${baseUrl!}/static/AdminLTE/plugins/jQuery/jQuery-2.2.0.min.js"></script>
    <!-- jQuery UI 1.11.4 -->
    <script src="${baseUrl!}/static/bootstrap/js/jquery-ui.js"></script>
</head>

<body class="hold-transition skin-blue sidebar-mini">

<div class="wrapper">
    <#include "/page/admin/common/header.ftl">
    <@header/>

    <!-- Left side column. contains the logo and sidebar -->
    <#include "/page/admin/common/left.ftl"/>
    <@pageLeft page_tab=page_tab/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <#nested />
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer">
        <strong>后台模板 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong>
    </footer>
    <#include "/page/admin/common/controlAside.ftl">
    <@controlAside/>
    <div class="control-sidebar-bg"></div>
</div>

<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.5 -->
<script src="${baseUrl!}/static/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${baseUrl!}/static/AdminLTE/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${baseUrl!}/static/AdminLTE/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- Morris.js charts -->
<script src="${baseUrl!}/static/js/raphael-min.js"></script>
<script src="${baseUrl!}/static/AdminLTE/plugins/morris/morris.min.js"></script>
<!-- Sparkline -->
<script src="${baseUrl!}/static/AdminLTE/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="${baseUrl!}/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${baseUrl!}/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- jQuery Knob Chart -->
<script src="${baseUrl!}/static/AdminLTE/plugins/knob/jquery.knob.js"></script>
<!-- daterangepicker -->
<script src="${baseUrl!}/static/js/moment.min.js"></script>
<script src="${baseUrl!}/static/AdminLTE/plugins/daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="${baseUrl!}/static/AdminLTE/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="${baseUrl!}/static/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="${baseUrl!}/static/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${baseUrl!}/static/AdminLTE/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${baseUrl!}/static/AdminLTE/dist/js/app.min.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="${baseUrl!}/static/AdminLTE/dist/js/pages/dashboard.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${baseUrl!}/static/AdminLTE/dist/js/demo.js"></script>
</body>
</html>
</#macro>