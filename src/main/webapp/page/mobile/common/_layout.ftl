<#macro html title description>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <title>${title!"JFinalbbs"}</title>
    <meta name="description" content="${description!} - ${siteTitle!}">
    <meta name="keywords" content="java,社区,jfinal,JFinalbbs,java社区,java论坛,论坛,bootstrap" />
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    ${qq_meta!}
    ${sina_meta!}
    ${baidu_site_meta!}
    ${google_site_meta!}
    ${bing_site_meta!}
    <link rel="icon" href="${baseUrl!}/static/favicon.ico">
    <link href="${baseUrl!}/static/mobile/css/mobile.css?v=20150604" rel="stylesheet">
    <script type="text/javascript" src="${baseUrl!}/static/bootstrap/js/jquery.min.js?v=20150604"></script>
    <script src="http://cdn.bootcss.com/blueimp-md5/2.1.0/js/md5.min.js"></script>
</head>
<body style="background-color: #e1e1e1;">
<#--页面头部信息-->
<#include "/page/mobile/common/header.ftl"/>
<@header/>
<#nested />
<#include "/page/mobile/common/footer.ftl"/>
<@footer/>
</body>
</html>
</#macro>