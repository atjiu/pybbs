<#macro footer>
<div class="container">
  <br>
  <div class="text-center">
    &copy;2016 Powered by <a href="https://yiiu.co" target="_blank">yiiu.co</a>
    <#if beianName??>
      <a href="http://www.miitbeian.gov.cn/">${beianName!}</a>
    </#if>
    ${tongjiJs!}
  </div>
  <br>
</div>
</#macro>