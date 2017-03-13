<#macro user_topics topics>
  <#list topics as topic>
  <div class="media">
    <div class="media-body">
      <div class="title">
        <a href="/topic/${topic.id!}">${topic.title!}</a>
      </div>
      <p>
        <a href="/?tab=${topic.tab}">${topic.tab}</a>
        <span>•</span>
        <span><a href="/user/${topic.user.username}">${topic.user.username}</a></span>
        <span class="hidden-sm hidden-xs">•</span>
        <span class="hidden-sm hidden-xs">${topic.replyCount!0}个回复</span>
        <span class="hidden-sm hidden-xs">•</span>
        <span class="hidden-sm hidden-xs">${topic.view!0}次浏览</span>
        <span>•</span>
        <span>${topic.formatDate(topic.inTime)}</span>
      </p>
    </div>
  </div>
    <#if topic_has_next>
    <div class="divide mar-top-5"></div>
    </#if>
  </#list>
</#macro>