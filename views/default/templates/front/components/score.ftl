<#macro score p=1 limit=10>
  <div class="panel panel-default">
    <div class="panel-heading">积分榜 <a href="/top100">TOP 100 >></a></div>
    <@score_tag p=p limit=limit>
      <table class="table">
        <tbody>
        <#list page.getContent() as user>
        <tr>
          <td><a href="/user/${user.username}">${user.username}</a></td>
          <td align="right">${user.score!0}</td>
        </tr>
        </#list>
        </tbody>
      </table>
    </@score_tag>
  </div>
</#macro>