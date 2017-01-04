<#macro searchresult topics>
<table class="table table-striped">
  <#list topics as topic>
    <tr>
      <td>
        <a href="/topic/${topic.id!}" target="_blank">${topic.lightTitle(q, topic.title)!}</a>
      </td>
    </tr>
    <tr>
      <td>
      ${topic.subContent(_editor, q, topic.content)!}
      </td>
    </tr>
  </#list>
</table>
</#macro>