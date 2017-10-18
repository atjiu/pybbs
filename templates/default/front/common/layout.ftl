<#macro html page_title page_tab="">
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>${page_title!site.name}</title>
  <link rel="icon" href="/favicon.ico">
  <link rel="stylesheet" href="/static/default/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="/static/default/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="/static/default/css/app.css">

  <script src="/static/default/js/jquery.min.js"></script>
  <script src="/static/default/bootstrap/js/bootstrap.min.js"></script>
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
<script src="/static/default/js/app.js"></script>
</body>
</html>
</#macro>