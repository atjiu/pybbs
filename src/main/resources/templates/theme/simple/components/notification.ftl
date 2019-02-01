<#macro notification userId read limit>
<@tag_notifications userId=userId read=read limit=limit>
  <#list notifications as notification>
    <div class="notification_${read}">
      <img width="24" src="${notification.avatar!}">
      <a href="/user/${notification.username}">${notification.username}</a>
      <span>${model.formatDate(notification.inTime)}</span>
      <#if notification.action == "COMMENT">
        评论了你的话题 <a href="/topic/${notification.topicId}">${notification.title}</a>
      <#elseif notification.action == "REPLY">
        在话题 <a href="/topic/${notification.topicId}">${notification.title}</a> 下回复了你
      <#elseif notification.action == "COLLECT">
        收藏了你的话题 <a href="/topic/${notification.topicId}">${notification.title}</a>
      </#if>
    </div>
    <div class="content">${model.formatContent(notification.content)}</div>
  </#list>
  <#if notifications?size == 0>
    无
  </#if>
</@tag_notifications>
</#macro>
