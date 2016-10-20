<#macro user_replies replies>
<table class="table table-striped">
  <#list replies as reply>
    <tr>
      <td>
      ${reply.formatDate(reply.inTime)!}
        回复了
        <a href="/user/${reply.user.username}">${reply.user.username}</a>
        创建的话题 › <a href="/topic/${reply.topic.id}">${reply.topic.title}</a>
      </td>
    </tr>
    <tr>
      <td>${reply.marked(reply.content!)}</td>
    </tr>
  </#list>
</table>
</#macro>