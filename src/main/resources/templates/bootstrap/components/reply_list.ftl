<#macro user_replies replies>
<table class="table table-striped">
  <#list replies as reply>
    <tr>
      <td>
        <a href="/user/${reply.user.username}">${reply.user.username}</a>
        ${reply.formatDate(reply.inTime)}
        <@spring.message "site.button.comment"/>
        <a href="/user/${reply.topic.user.username}">${reply.topic.user.username}</a>
        <@spring.message "site.panel.body.topic"/> › <a href="/topic/${reply.topic.id}">${reply.topic.title}</a>
      </td>
    </tr>
    <tr>
      <td>${reply.marked(reply.content)}</td>
    </tr>
  </#list>
</table>
</#macro>