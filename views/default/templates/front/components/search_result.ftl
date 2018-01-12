<#macro searchresult topics>
<table class="table table-striped">
  <#list topics as topic>
    <tr>
      <td>
        <a href="/topic/${topic.id?c}" target="_blank" style="font-size: 16px;">${topic.title!}</a>
      </td>
    </tr>
    <tr>
      <td class="search-result topic-detail-content">
        ${model.marked(topic.content!, true)}
      </td>
    </tr>
  </#list>
</table>
</#macro>