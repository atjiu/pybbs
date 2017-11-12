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
      <#list page.getContent() as collect>
        <div class="media">
          <div class="media-body">
            <div class="title">
              <a href="/topic/${collect.topic.id!}">${collect.topic.title!?html}</a>
            </div>
            <p>
              <a href="/go/${collect.topic.node.value!}">${collect.topic.node.name!}</a>
              <span>•</span>
              <span><a href="/user/${collect.topic.user.username}">${collect.topic.user.username}</a></span>
              <span class="hidden-sm hidden-xs">•</span>
              <span class="hidden-sm hidden-xs">${collect.topic.replyCount!0}个回复</span>
              <span class="hidden-sm hidden-xs">•</span>
              <span class="hidden-sm hidden-xs">${collect.topic.view!0}次浏览</span>
              <span>•</span>
              <span>${model.formatDate(collect.topic.inTime)}</span>
            </p>
          </div>
        </div>
        <#if collect_has_next>
          <div class="divide mar-top-5"></div>
        </#if>
      </div>
      </#list>
    </#if>
    <#include "paginate.ftl"/>
    <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/collects" urlParas=""/>
  </div>
  </@user_collects_tag>
</#macro>