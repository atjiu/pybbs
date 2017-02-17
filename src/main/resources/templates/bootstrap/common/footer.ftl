<#macro footer>
<div class="container">
  <br>
  <span>
    &copy;2016 Powered by <a href="//bbs.tomoya.cn" target="_blank"><@spring.message "site.name"/></a>
    <a href="http://www.miitbeian.gov.cn/">${beianName!}</a>
  ${tongjiJs!}
  </span>
  <span class="pull-right">
    <span class="glyphicon glyphicon-globe"></span> <a href="/changeLanguage?lang=zh">中文</a> / <a href="/changeLanguage?lang=en">English</a>
  </span>
  <div style="clear: both;"></div>
  <br>
</div>
</#macro>