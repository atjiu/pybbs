<#macro user_replies replies>
<table class="table table-striped">
  <#list replies as reply>
    <tr>
      <td>
        <a href="/user/${reply.user.username}">${reply.user.username}</a>
        ${model.formatDate(reply.inTime)}
        回复了
        <a href="/user/${reply.topic.user.username}">${reply.topic.user.username}</a>
        创建的话题 › <a href="/topic/${reply.topic.id}">${reply.topic.title!?html}</a>
      </td>
    </tr>
    <tr>
      <td>${model.marked(reply.content, true)}</td>
    </tr>
  </#list>
</table>
</#macro>