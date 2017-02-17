<#macro topics>
  <#list page.getContent() as topic>
  <div class="media">
    <div class="media-left">
      <a href="/user/${topic.user.username!}"><img src="${topic.user.avatar}" class="avatar" alt=""></a>
    </div>
    <div class="media-body">
      <div class="title">
        <a href="/topic/${topic.id!}">${topic.title!}</a>
      </div>
      <p class="gray">
        <#if topic.top == true>
          <span class="label label-primary"><@spring.message "site.panel.body.top"/></span>
        <#elseif topic.good == true>
          <span class="label label-success"><@spring.message "site.panel.body.good"/></span>
        <#else>
          <a href="/?tab=${topic.tab!}">${topic.tab!}</a>
        </#if>
        <span>•</span>
        <span><a href="/user/${topic.user.username!}">${topic.user.username!}</a></span>
        <span class="hidden-sm hidden-xs">•</span>
        <span class="hidden-sm hidden-xs">${topic.replyCount!0} <@spring.message "site.panel.body.comments"/></span>
        <span class="hidden-sm hidden-xs">•</span>
        <span class="hidden-sm hidden-xs">${topic.view!0} <@spring.message "site.panel.body.views"/></span>
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