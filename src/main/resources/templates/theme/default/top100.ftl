<#include "layout/layout.ftl"/>
<@html page_title="Top100" page_tab="">
<div class="row">
  <div class="col-md-9">
    <#include "components/score.ftl"/>
    <@score limit=100 top100=true/>
  </div>
  <div class="col-md-3 hidden-xs">
    <#if _user??>
      <#include "components/user_info.ftl"/>
    <#else>
      <#include "components/welcome.ftl"/>
    </#if>
  </div>
</div>
</@html>
