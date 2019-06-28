<#macro html page_title page_tab>
  <!doctype html>
  <html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <#--<meta name="viewport"-->
          <#--content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${page_title!} - ${site.name}</title>
    <link rel="icon" href="https://yiiu.co/favicon.ico">
    <#--css-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/static/theme/${site.theme}/css/app.css"/>
    <#--javascript-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  </head>
  <body>
  <#include "../components/loading.ftl"/>
  <div class="container">
    <#include "header.ftl"/>
    <@header page_tab=page_tab/>
    <section><#nested /></section>
    <#include "footer.ftl"/>
  </div>
  </body>
  </html>
</#macro>
