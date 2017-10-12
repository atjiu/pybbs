<#macro html page_title page_tab="">
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>${page_title!site.name}</title>
  <link rel="icon" href="//tomoya.cn/favicon.ico">
  <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="/static/default/css/pybbs.css">

  <script src="//cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="wrapper">
  <#include "./header.ftl">
  <@header page_tab=page_tab/>
  <div class="container">
    <#nested />
  </div>
</div>
  <#include "./footer.ftl">
  <@footer/>
<script src="/static/default/js/pybbs.js"></script>
</body>
</html>
</#macro>