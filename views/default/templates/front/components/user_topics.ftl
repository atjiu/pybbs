<#macro user_topics username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_topics_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${username}创建的话题</div>
    <#if page.totalCount == 0>
      <div class="panel-body">
        暂无话题
      </div>
    <#else>
      <div class="panel-body paginate-bot">
        <#list page.getContent() as topic>
          <div class="media">
            <div class="media-body">
              <div class="title">
                <a data-pjax href="/topic/${topic.id}">
                  ${topic.title!?html}
                </a>
              </div>
              <p>
                <i class="fa fa-chevron-up"></i>
                <i class="fa fa-chevron-down"></i>
                <span>${topic.up - topic.down}</span>
                <span class="hidden-sm hidden-xs">•</span>
                <span><a data-pjax href="/user/${topic.username}">${topic.username}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs"><a data-pjax href="/topic/${topic.id}">${topic.comment_count!0}个评论</a></span>
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
        <#if isPaginate>
          <#include "paginate.ftl"/>
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/user/${username}/topics" urlParas=""/>
        </#if>
      </div>
      <#if isFooter>
        <div class="panel-footer">
          <a data-pjax href="/user/${username}/topics">${username}更多话题&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  </@user_topics_tag>
</#macro>