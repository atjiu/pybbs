<#include "../layout/" + layoutName/>
<@html page_title="通知">
<div class="row">
  <div class="col-md-9">
    <@notifications_tag p=p>
      <div class="panel panel-default">
        <div class="panel-heading">
          通知
          <span class="pull-right">总共收到通知 ${page.totalCount!}</span>
        </div>
        <div class="panel-body">
          <#list page.getContent() as notification>
            <div class="media">
              <div class="media-left">
                <img src="${notification.avatar}" class="avatar-sm">
              </div>
              <div class="media-body">
                <div class="gray" <#if !notification.is_read>style="font-weight:700;"</#if>>
                  <a data-pjax href="/user/${notification.username}">${notification.username}</a>
                  <#if notification.action == "COLLECT">
                    收藏了你发布的话题
                  <#elseif notification.action == "COMMENT">
                    在
                  <#elseif notification.action == "REPLY"
                        || notification.action == "UP_COMMENT"
                        || notification.action == "DOWN_COMMENT">
                    在话题
                  <#elseif notification.action == "UP_TOPIC">
                    赞了你的话题
                  <#elseif notification.action == "DOWN_TOPIC">
                    踩了你的话题
                  </#if>
                  <a data-pjax href="/topic/${notification.topic_id}">${notification.topic_title!?html}</a>
                  <#if notification.action == "COMMENT">
                    里评论了你
                  <#elseif notification.action == "REPLY">
                    里回复了你的评论
                  <#elseif notification.action == "UP_COMMENT">
                    里赞了你的评论
                  <#elseif notification.action == "DOWN_COMMENT">
                    里踩了你的评论
                  </#if>
                  <span>${model.formatDate(notification.in_time)}</span>
                </div>
                <#if notification.content?? && notification.content != "">
                  <div class="payload">
                    ${notification.content!}
                  </div>
                </#if>
              </div>
            </div>
            <#if notification_has_next>
              <div class="divide mar-top-5"></div>
            </#if>
          </#list>
          <#include "../components/paginate.ftl"/>
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/notification/list"/>
        </div>
      </div>
    </@notifications_tag>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>