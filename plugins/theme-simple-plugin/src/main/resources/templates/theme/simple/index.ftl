<#include "layout/layout.ftl"/>
<@html page_title="首页" page_tab=tab>
    <#if active?? && active>
      <div style="color: #3c763d;">激活成功</div>
    </#if>
    <@tag_topics pageNo=pageNo tab=tab>
        <#include "components/topics.ftl"/>
        <@topics page=page/>

        <#if page.current < page.pages>
          <a href="/?tab=${tab}&pageNo=${page.current + 1}">查看更多</a>
        </#if>
    </@tag_topics>
</@html>
