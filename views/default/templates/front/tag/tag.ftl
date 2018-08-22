<#include "../layout/" + layoutName/>
<@html page_title="${tag.name}">
<div class="row">
  <div class="panel panel-default">
    <div class="panel-body">
      <h4 style="margin-top: 0; margin-bottom: 10px;">
        <#if tag.logo??>
          <img src="${tag.logo}" width="32" alt="">
        </#if>
        ${tag.name}
        <small>共有${tag.topicCount!0}篇话题</small>
      </h4>
      <small>${tag.intro!}</small>
      <span></span>
    </div>
    <div class="divide"></div>
    <div class="panel-body paginate-bot">
      <#include "../components/topics.ftl"/>
      <@topics topics=page.content/>
      <#include "../components/paginate.ftl"/>
      <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/topic/tag/${tag.name}"/>
    </div>
  </div>
</div>
</@html>