<#macro user_topics username pageNo pageSize paginate=false>
  <@tag_user_topics username=username pageNo=pageNo pageSize=pageSize>
    <ul>
      <#list topics.records as topic>
        <li><a href="/topic/${topic.id}">${topic.title}</a></li>
      </#list>
    </ul>
    <#if paginate && topics.current &lt; topics.pages>
      <a href="/user/${username}/topics?pageNo=${topics.current + 1}">查看更多</a>
    </#if>
  </@tag_user_topics>
</#macro>
