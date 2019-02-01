<#include "layout/layout.ftl"/>
<@html page_title="搜索" page_tab="">
  搜索 <b>${keyword!} 结果</b>
  <@tag_search pageNo=pageNo keyword=keyword>
    <table border="0" style="width: 100%; margin-top: 10px;">
      <#list page.records as map>
        <tr>
          <td><a href="/topic/${map.id!}" target="_blank" style="font-size: 16px;">${map.title!}</a></td>
        </tr>
      </#list>
    </table>
    <#if page.current < page.pages>
      <a href="/search?keyword=${keyword!}&pageNo=${page.current + 1}">查看更多</a>
    </#if>
  </@tag_search>
</@html>
