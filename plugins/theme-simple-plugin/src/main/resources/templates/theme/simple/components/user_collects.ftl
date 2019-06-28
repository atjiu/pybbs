<#macro user_collects username pageNo pageSize paginate=false>
  <@tag_user_collects username=username pageNo=pageNo pageSize=pageSize>
    <ul>
      <#list collects.records as collect>
        <li><a href="/topic/${collect.id}">${collect.title}</a></li>
      </#list>
    </ul>
    <#if paginate && collects.current &lt; collects.pages>
      <a href="/user/${username}/collects?pageNo=${collects.current + 1}">查看更多</a>
    </#if>
  </@tag_user_collects>
</#macro>
