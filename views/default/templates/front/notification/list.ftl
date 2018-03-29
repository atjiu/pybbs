<#include "../layout/layout.ftl"/>
<@html page_title="通知">
<div class="row">
  <div class="col-md-9">
    <@notifications_tag p=p>
      <div class="panel panel-default">
        <div class="panel-heading">
          通知
          <span class="pull-right">总共收到通知 ${page.getTotalElements()!}</span>
        </div>
        <div class="panel-body">
          <#list page.getContent() as map>
            <div class="media">
              <div class="media-left">
                <img src="${map.user.avatar}" class="avatar-sm">
              </div>
              <div class="media-body">
                <div class="gray" <#if !map.notification.isRead>style="font-weight:700;"</#if>>
                  <a href="/user/${map.user.username}">${map.user.username}</a>
                  <#if map.notification.action == "COLLECT">
                    收藏了你发布的话题
                  <#elseif map.notification.action == "COMMENT">
                    在
                  <#elseif map.notification.action == "REPLY"
                        || map.notification.action == "UP_COMMENT"
                        || map.notification.action == "DOWN_COMMENT">
                    在话题
                  <#elseif map.notification.action == "UP_TOPIC">
                    赞了你的话题
                  <#elseif map.notification.action == "DOWN_TOPIC">
                    踩了你的话题
                  </#if>
                  <a href="/topic/${map.topic.id}">${map.topic.title!?html}</a>
                  <#if map.notification.action == "COMMENT">
                    里评论了你
                  <#elseif map.notification.action == "REPLY">
                    里回复了你的评论
                  <#elseif map.notification.action == "UP_COMMENT">
                    里赞了你的评论
                  <#elseif map.notification.action == "DOWN_COMMENT">
                    里踩了你的评论
                  </#if>
                  <span>${model.formatDate(map.notification.inTime)}</span>
                </div>
                <#if map.notification.content?? && map.notification.content != "">
                  <div class="payload">
                    ${map.notification.content!}
                  </div>
                </#if>
              </div>
            </div>
            <#if map_has_next>
              <div class="divide mar-top-5"></div>
            </#if>
          </#list>
          <#include "../components/paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/notification/list"/>
        </div>
      </div>
    </@notifications_tag>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>