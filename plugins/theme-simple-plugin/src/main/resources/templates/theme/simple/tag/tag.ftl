<#include "../layout/layout.ftl"/>
<@html page_title="标签" page_tab="">
  <table border="0" style="width: 100%">
    <tr>
      <td>
        <#if tag.icon??>
          <img width="24" style="vertical-align: middle;" src="${tag.icon}" alt="">&nbsp;
        </#if>
        <b>${tag.name}</b>&nbsp;
        <span>共有 ${tag.topicCount!0} 篇话题</span>
        <#if _user??>
          <a class="pull-right" href="/topic/create?tag=${tag.name}">发布话题</a>
        </#if>
      </td>
    </tr>
    <#if tag.description??>
      <tr>
        <td style="font-size: 14px; padding-top: 10px;">${tag.description}</td>
      </tr>
    </#if>
  </table>
  <hr>
  <#include "../components/topics.ftl"/>
  <@topics page=page/>
</@html>
