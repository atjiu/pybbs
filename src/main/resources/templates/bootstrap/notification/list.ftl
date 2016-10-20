<#include "../common/layout.ftl"/>
<@html page_title="通知" page_tab="notification">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        通知
        <span class="pull-right">总共收到通知 ${page.getTotalElements()!}</span>
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
                  收藏了你发布的话题
                <#elseif notification.action == "REPLY">
                  在
                <#elseif notification.action == "AT">
                  在回复
                </#if>
                <a href="/topic/${notification.topic.id}">${notification.topic.title}</a>
                <#if notification.action == "REPLY">
                  里回复了你
                <#elseif notification.action == "AT">
                  时提到了你
                </#if>
                <span>${notification.formatDate(notification.inTime)}</span>
              </div>
              <#if notification.content?? && notification.content != "">
                <div class="payload">
                ${notification.marked(notification.content)}
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