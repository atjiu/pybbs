<#macro notification userId read limit>
  <@tag_notifications userId=userId read=read limit=limit>
    <#list notifications as notification>
      <div class="media">
        <div class="media-left">
          <img src="${notification.avatar}" class="avatar avatar-sm">
        </div>
        <div class="media-body">
          <div class="gray" <#if !notification.read>style="font-weight:700;"</#if>>
            <a data-pjax href="/user/${notification.username}">${notification.username}</a>
            <#if notification.action == "COLLECT">
              收藏了你发布的话题
            <#elseif notification.action == "COMMENT">
              在
            <#elseif notification.action == "REPLY"
            || notification.action == "UP_COMMENT"
            || notification.action == "DOWN_COMMENT">
              在话题
            <#--<#elseif notification.action == "UP_TOPIC">
              赞了你的话题
            <#elseif notification.action == "DOWN_TOPIC">
              踩了你的话题-->
            </#if>
            <a data-pjax href="/topic/${notification.topic_id}">${notification.topic_title!?html}</a>
            <#if notification.action == "COMMENT">
              里评论了你
            <#elseif notification.action == "REPLY">
              里回复了你的评论
            <#--<#elseif notification.action == "UP_COMMENT">
              里赞了你的评论
            <#elseif notification.action == "DOWN_COMMENT">
              里踩了你的评论-->
            </#if>
            <span>${model.formatDate(notification.in_time)}</span>
          </div>
          <#if notification.content??>
            <div class="payload">
              ${model.formatContent(notification.content)}
            </div>
          </#if>
        </div>
      </div>
      <#if notification_has_next>
        <div class="divide"></div>
      </#if>
    </#list>
  </@tag_notifications>
</#macro>
