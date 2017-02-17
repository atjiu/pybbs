<#macro user_replies replies>
<table class="table table-striped">
  <#list replies as reply>
    <tr>
      <td>
      ${reply.formatDate(reply.inTime)!}
        <@spring.message "site.panel.body.comment"/>
        <a href="/user/${reply.user.username}">${reply.user.username}</a>
        <@spring.message "site.panel.body.topic"/> › <a href="/topic/${reply.topic.id}">${reply.topic.title}</a>
      </td>
    </tr>
    <tr class="user_replies">
      <td>
        <#if reply.editor?? && reply.editor  == 'markdown'>
          ${reply.marked(reply.content!)}
        <#elseif reply.editor?? && reply.editor  == 'wangeditor'>
          ${reply.content!}
        </#if>
      </td>
    </tr>
  </#list>
</table>
</#macro>