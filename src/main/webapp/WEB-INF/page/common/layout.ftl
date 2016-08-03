<#macro html page_title page_tab="">
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    ${weibometa!}
    <title>${page_title!siteTitle}</title>
    <link rel="icon" href="/static/favicon.ico">

    <link rel="stylesheet" href="/static/css/bootstrap.css"/>
    <#--<link rel="stylesheet/less" href="/static/css/less/bootstrap.less">-->
    <#--<script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>-->
    <link rel="stylesheet" href="/static/css/github.css">
    <link rel="stylesheet" href="/static/css/pybbs.css">
    <script src="//cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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
<script src="/static/js/pybbs.js"></script>
</body>
</html>
</#macro>