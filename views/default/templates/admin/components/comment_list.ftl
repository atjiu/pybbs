<#macro user_replies replies>
<table class="table table-striped">
  <#list replies as comment>
    <tr>
      <td>
        <a href="/user/${comment.user.username}">${comment.user.username}</a>
        ${model.formatDate(comment.inTime)}
        评论了
        <a href="/user/${comment.topic.user.username}">${comment.topic.user.username}</a>
        创建的话题 › <a href="/topic/${comment.topic.id}">${comment.topic.title!?html}</a>
      </td>
    </tr>
    <tr>
      <td>${model.marked(comment.content, true)}</td>
    </tr>
  </#list>
</table>
</#macro>