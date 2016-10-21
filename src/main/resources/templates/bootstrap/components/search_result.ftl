<#macro searchresult topics>
<table class="table table-striped">
  <#list topics as topic>
    <tr>
      <td>
        <a href="/topic/${topic.topicId}" target="_blank">${topic.title!}</a>
      </td>
    </tr>
    <tr>
      <td>
      ${topic.content!}
      </td>
    </tr>
  </#list>
</table>
</#macro>