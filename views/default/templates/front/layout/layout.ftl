<#macro html page_title page_tab="">
<!doctype html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <#if site.googleZZ??>
    <meta name="google-site-verification" content="${site.googleZZ}" />
  </#if>
  <#if site.baiduZZ??>
    <meta name="baidu-site-verification" content="${site.baiduZZ}" />
  </#if>
  <title>${page_title!site.name}</title>
  <link rel="shortcut icon" href="/static/favicon.svg">
  <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="/static/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="/static/jquery-toast-plugin/jquery.toast.min.css">
  <link rel="stylesheet" href="/static/css/app.css">

  <#if site.GA?? && site.GA != "">
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=${site.GA}"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', '${site.GA}');
    </script>
  </#if>

  <#if site.baiduTJ?? && site.baiduTJ != "">
    <script>
      var _hmt = _hmt || [];
      (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?${site.baiduTJ}";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
      })();
    </script>
  </#if>

  <script src="/static/js/jquery.min.js"></script>
  <script src="/static/js/jquery.pjax.min.js"></script>
  <script src="/static/bootstrap/js/bootstrap.min.js"></script>
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
  </script>
</head>
<body>
<div class="wrapper">
  <#include "./header.ftl">
  <@header page_tab=page_tab/>
  <div class="container" style="padding: 0 25px;">
    <form class="hidden-lg hidden-md" style="margin: 0 -10px;" role="search" action="/search" method="get">
      <div class="form-group has-feedback" style="margin-bottom: 10px;">
        <input type="text" class="form-control" name="q" value="${q!}" placeholder="回车搜索">
        <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
      </div>
    </form>
    <div id="pjax-container">
      <#nested />
    </div>
  </div>
</div>
  <#include "./footer.ftl">
  <@footer/>
  <script>
    $(function () {
      var n = $("#goTop");
      n.click(function () {
        return $("html,body").animate({
          scrollTop: 0
        });
      });
    });
    (function ($) {
      $(document).pjax('[data-pjax] a, a[data-pjax]', '#pjax-container', {
        timeout: 5000
      });
      $(document).on('pjax:start', function() {
        NProgress.start();
      });
      $(document).on('pjax:complete', function() {
        NProgress.done();
      });
      // $(document).on('pjax:timeout', function() {
      //   toast("请求超时", "error");
      // });
    })(jQuery);
  </script>
</body>
</html>
</#macro>