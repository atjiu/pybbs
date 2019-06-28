<#include "../layout/layout.ftl"/>
<@html page_title="标签" page_tab="tags">
  <div class="tags">
    <@tag_tags pageNo=pageNo pageSize=40>
      <#list page.records as tag>
        <div class="item">
          <#if tag.icon??>
            <img src="${tag.icon}" width="24" style="vertical-align: middle;" alt="">
          </#if>
          <b><a href="/topic/tag/${tag.name}">${tag.name}</a></b> X ${tag.topicCount!0}
          <#if tag.description??>
            <div class="description">${tag.description}</div>
          </#if>
        </div>
        <#if (tag_index + 1) % 4 == 0>
          <hr>
        </#if>
      </#list>
      <div class="clear"></div>
      <#if page.current < page.pages>
        <a href="/tags?pageNo=${page.current + 1}">查看更多</a>
      </#if>
    </@tag_tags>
  </div>
</@html>
