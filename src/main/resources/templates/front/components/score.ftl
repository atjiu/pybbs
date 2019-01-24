<#macro score limit top100=false>
  <div class="panel panel-info">
    <div class="panel-heading">
      积分榜
      <#if !top100>
        <span class="pull-right"><a href="/top100">Top100</a></span>
      </#if>
    </div>
    <table class="table">
      <#if top100>
        <tr>
          <th>用户名</th>
          <th>积分</th>
        </tr>
      </#if>
      <@tag_score limit=limit>
        <#list users as user>
          <tr>
            <td><a href="/user/${user.username}">${user.username}</a></td>
            <td>${user.score}</td>
          </tr>
        </#list>
      </@tag_score>
    </table>
  </div>
</#macro>
