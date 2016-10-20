<#macro user_collect collects>
  <#list collects as collect>
  <div class="media">
    <div class="media-body">
      <div class="title">
        <a href="/topic/${collect.topic.id!}">${collect.topic.title!}</a>
      </div>
      <p>
        <a href="/?tab=${collect.topic.tab}">${collect.topic.tab}</a>
        <span>•</span>
        <span><a href="/user/${collect.topic.user.username}">${collect.topic.user.username}</a></span>
        <span class="hidden-sm hidden-xs">•</span>
        <span class="hidden-sm hidden-xs">${collect.topic.replyCount!0}个回复</span>
        <span class="hidden-sm hidden-xs">•</span>
        <span class="hidden-sm hidden-xs">${collect.topic.view!0}次浏览</span>
        <span>•</span>
        <span>${collect.formatDate(collect.topic.inTime)}</span>
      </p>
    </div>
  </div>
    <#if collect_has_next>
    <div class="divide mar-top-5"></div>
    </#if>
  </#list>
</#macro>