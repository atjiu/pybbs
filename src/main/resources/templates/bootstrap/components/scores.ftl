<#macro userscores limit hidden="true">
<div class="panel panel-default">
  <div class="panel-heading">
    积分榜
    <#if hidden="true">
      <span class="pull-right"><a href="/top100">TOP100 &gt;&gt; </a></span>
    </#if>
  </div>
<#--<div class="panel-body">-->
  <table class="table table-striped">
    <@py.scores limit=limit>
      <#list list as u>
        <tr>
          <td><a href="/user/${u.nickname!}">${u.nickname!}</a></td>
          <td <#if hidden == "true">class="hidden"</#if>><a href="${u.url!}">${u.url!}</a></td>
          <td <#if hidden == "true">class="hidden"</#if>>${u.description!}</td>
          <td>${u.score!}</td>
        </tr>
      </#list>
    </@py.scores>
  </table>
<#--</div>-->
</div>
</#macro>