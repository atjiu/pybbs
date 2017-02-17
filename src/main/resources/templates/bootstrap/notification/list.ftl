<#include "../common/layout.ftl"/>
<@html page_tab="notification">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <@spring.message "site.panel.header.notifications"/>
        <span class="pull-right"><@spring.message "site.panel.header.totalNotice"/> ${page.getTotalElements()!}</span>
      </div>
      <div class="panel-body">
        <#list page.getContent() as notification>
          <div class="media">
            <div class="media-left">
              <img src="${notification.user.avatar}" class="avatar-sm">
            </div>
            <div class="media-body">
              <div class="gray" <#if notification.read == false>style="font-weight:700;"</#if>>
                <a href="/user/${notification.user.username}">${notification.user.username}</a>
                <#if notification.action == "COLLECT">
                  <@spring.message "site.panel.body.collectTopic"/>
                <#elseif notification.action == "REPLY">
                  <@spring.message "site.panel.body.at"/>
                <#elseif notification.action == "AT">
                  <@spring.message "site.panel.body.comment"/>
                </#if>
                <a href="/topic/${notification.topic.id}">${notification.topic.title}</a>
                <#if notification.action == "REPLY">
                  <@spring.message "site.panel.body.reply"/>
                <#elseif notification.action == "AT">
                  <@spring.message "site.panel.body.mentioned"/>
                </#if>
                <span>${notification.formatDate(notification.inTime)}</span>
              </div>
              <#if notification.content?? && notification.content != "">
                <div class="payload">
                  <#if notification.editor?? && notification.editor  == 'markdown'>
                    ${notification.marked(notification.content)}
                  <#elseif notification.editor?? && notification.editor == 'wangeditor'>
                  ${notification.content!}
                  </#if>
                </div>
              </#if>
            </div>
          </div>
          <#if notification_has_next>
            <div class="divide mar-top-5"></div>
          </#if>
        </#list>
        <#include "../components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/notification/list"/>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>