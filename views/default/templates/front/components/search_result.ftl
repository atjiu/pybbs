<#macro searchresult topics>
  <table class="table">
    <#list topics as topic>
      <tr>
        <td><a data-pjax href="/topic/${topic.id!}" target="_blank" style="font-size: 16px;">${topic.title!}</a></td>
      </tr>
    </#list>
  </table>
</#macro>