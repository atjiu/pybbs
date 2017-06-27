<#include "../common/layout.ftl"/>
<@html page_title=label.name + "标签相关话题" page_tab="label">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body">
        <div class="media">
          <div class="media-left">
            <#if label.image??>
              <img src="${label.image!}" class="avatar">
            <#else>
              <div class="topic-label">
                <span class="glyphicon glyphicon-tags"></span>
              </div>
            </#if>
          </div>
          <div class="media-body">
            <div class="title">
              ${label.name}
            </div>
            <p class="gray">${label.intro!}</p>
          </div>
        </div>
      </div>
      <div class="divide"></div>
      <@topics_tag p=p labelId=label.id>
        <div class="panel-body paginate-bot">
          <#list page.getContent() as topic>
            <div class="media">
              <div class="media-left">
                <a href="/user/${topic.user.username!}"><img src="${topic.user.avatar}" class="avatar" alt=""></a>
              </div>
              <div class="media-body">
                <div class="title">
                  <a href="/topic/${topic.id!}">${topic.title!}</a>
                </div>
                <p class="gray">
                  <#if topic.top == true>
                    <span class="label label-primary">置顶</span>
                  <#elseif topic.good == true>
                    <span class="label label-success">精华</span>
                  <#else>
                    <a href="/?tab=${topic.tab!}">${topic.tab!}</a>
                  </#if>
                  <span>•</span>
                  <span><a href="/user/${topic.user.username!}">${topic.user.username!}</a></span>
                  <span class="hidden-sm hidden-xs">•</span>
                  <span class="hidden-sm hidden-xs">${topic.replyCount!0}个回复</span>
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
          <#include "../components/paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/label/${label.name}"/>
        </div>
      </@topics_tag>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>