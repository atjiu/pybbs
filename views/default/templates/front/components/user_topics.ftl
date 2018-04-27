<#macro user_topics username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_topics_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${username}创建的话题</div>
    <#if page.getTotalElements() == 0>
      <div class="panel-body">
        暂无话题
      </div>
    <#else>
      <div class="panel-body paginate-bot">
        <#list page.getContent() as map>
          <div class="media">
            <div class="media-body">
              <div class="title">
                <#if model.isEmpty(map.topic.url)>
                  <a href="/topic/${map.topic.id}">
                    ${map.topic.title!?html}
                  </a>
                <#else>
                  <a href="${map.topic.url!?html}" target="_blank">
                    ${map.topic.title!?html}
                    <i class="glyphicon glyphicon-link"></i>
                  </a>
                </#if>
              </div>
              <p>
                <i class="fa fa-chevron-up"></i>
                <i class="fa fa-chevron-down"></i>
                <span>${map.topic.up - map.topic.down}</span>
                <span class="hidden-sm hidden-xs">•</span>
                <span><a href="/user/${map.user.username}">${map.user.username}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs"><a href="/topic/${map.topic.id!c}">${map.topic.commentCount!0}个评论</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${map.topic.view!0}次浏览</span>
                <span>•</span>
                <span>${model.formatDate(map.topic.inTime)}</span>
              </p>
            </div>
          </div>
          <#if map_has_next>
            <div class="divide mar-top-5"></div>
          </#if>
        </#list>
        <#if isPaginate>
          <#include "paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/topics" urlParas=""/>
        </#if>
      </div>
      <#if isFooter>
        <div class="panel-footer">
          <a href="/user/${username}/topics">${username}更多话题&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  </@user_topics_tag>
</#macro>