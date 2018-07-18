<#macro user_collect username p=1 limit=site.pageSize>
  <@user_collects_tag username=username p=p>
  <div class="panel panel-default">
    <div class="panel-heading">
      ${currentUser.username}收藏的话题
    </div>
    <#if page.getTotalElements() == 0>
      <div class="panel-body">
        暂无收藏
      </div>
    <#else>
      <div class="panel-body">
        <#list page.getContent() as map>
          <div class="media">
            <div class="media-body">
              <div class="title">
                <a href="/topic/${map.topic.id!}">${map.topic.title!?html}</a>
              </div>
              <p>
                <span><a href="/user/${map.user.username}">${map.user.username}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${map.topic.commentCount!0}个评论</span>
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
      </div>
    </#if>
    <#include "paginate.ftl"/>
    <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/collects" urlParas=""/>
  </div>
  </@user_collects_tag>
</#macro>