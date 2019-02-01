<#include "layout/layout.ftl"/>
<@html page_title="首页" page_tab=tab>
<@tag_topics pageNo=pageNo tab=tab>
  <#include "./components/topics.ftl"/>
  <@topics page=page/>

  <#if page.current < page.pages>
    <a href="/?tab=${tab}&pageNo=${page.current + 1}">查看更多</a>
  </#if>
</@tag_topics>
</@html>
