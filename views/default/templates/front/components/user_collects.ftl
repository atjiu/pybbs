<#macro user_collect username p=1 limit=site.pageSize>
  <@user_collects_tag username=username p=p>
  <div class="panel panel-default">
    <div class="panel-heading">
      ${currentUser.username}收藏的话题
    </div>
    <#if page.totalCount == 0>
      <div class="panel-body">
        暂无收藏
      </div>
    <#else>
      <div class="panel-body">
        <#list page.getContent() as topic>
          <div class="media">
            <div class="media-body">
              <div class="title">
                <a data-pjax href="/topic/${topic.id!}">${topic.title!?html}</a>
              </div>
              <p>
                <span><a data-pjax href="/user/${topic.username}">${topic.username}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.comment_count!0}个评论</span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.view!0}次浏览</span>
                <span>•</span>
                <span>${model.formatDate(topic.in_time)}</span>
              </p>
            </div>
          </div>
          <#if topic_has_next>
            <div class="divide mar-top-5"></div>
          </#if>
        </#list>
      </div>
    </#if>
    <#include "paginate.ftl"/>
    <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/user/${username}/collects" urlParas=""/>
  </div>
  </@user_collects_tag>
</#macro>