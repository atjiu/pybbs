<#include "common/layout.ftl"/>
<@html page_title="积分前100 - ${siteTitle!}">
<div class="row">
  <div class="col-md-9">
    <#include "./components/scores.ftl"/>
            <@userscores limit=100 hidden="false" />
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>