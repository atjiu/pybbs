<#macro reputation p=1 limit=10>
  <div class="panel panel-default">
    <div class="panel-heading">声望榜 <a data-pjax href="/top100">TOP 100 >></a></div>
    <@reputation_tag p=p limit=limit>
      <table class="table">
        <tbody>
        <#list page.getContent() as user>
        <tr>
          <td><a data-pjax href="/user/${user.username}">${user.username}</a></td>
          <td align="right">${user.reputation!0}</td>
        </tr>
        </#list>
        </tbody>
      </table>
    </@reputation_tag>
  </div>
</#macro>