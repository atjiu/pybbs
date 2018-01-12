<#macro user_topics username p=1 limit=site.pageSize isPaginate=false isFooter=false>
  <@user_topics_tag username=username p=p limit=limit>
  <div class="panel panel-default">
    <div class="panel-heading">${username}创建的话题</div>
    <#if page.getTotalElements() == 0>
      <div class="panel-body">
        暂无话题
      </div>
    <#else>
      <div class="panel-body">
        <#list page.getContent() as topic>
          <div class="media">
            <div class="media-body">
              <div class="title">
                <#if model.isEmpty(topic.url)>
                  <a href="/topic/${topic.id?c}">
                    ${topic.title!?html}
                  </a>
                <#else>
                  <a href="${topic.url!?html}" target="_blank">
                    ${topic.title!?html}
                    <i class="glyphicon glyphicon-link"></i>
                  </a>
                </#if>
              </div>
              <p>
                <a href="/go/${topic.node.value!}">${topic.node.name!}</a>
                <span>•</span>
                <span><a href="/user/${topic.user.username}">${topic.user.username}</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs"><a href="/topic/${topic.id!c}">${topic.replyCount!0}个回复</a></span>
                <span class="hidden-sm hidden-xs">•</span>
                <span class="hidden-sm hidden-xs">${topic.view!0}次浏览</span>
                <span>•</span>
                <span>${model.formatDate(topic.inTime)}</span>
              </p>
            </div>
          </div>
          <#if topic_has_next>
            <div class="divide mar-top-5"></div>
          </#if>
        </#list>
        <#if isPaginate>
          <#include "paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${username}/topics" urlParas=""/>
        </#if>
      </div>
      <#if isFooter || page.getTotalElements() &gt;= limit>
        <div class="panel-footer">
          <a href="/user/${username}/topics">${username}更多话题&gt;&gt;</a>
        </div>
      </#if>
    </#if>
  </div>
  </@user_topics_tag>
</#macro>