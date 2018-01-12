<#include "../common/layout.ftl"/>
<@html page_title="${node.name}">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-body">
        <h4>
          ${node.name}
          <small>共有${node.topicCount!0}篇话题</small>
        </h4>
        <small>${node.intro!}</small>
        <span></span>
      </div>
      <div class="divide"></div>
      <@node_topics_tag p=p value=value>
      <div class="panel-body paginate-bot">
        <#list page.getContent() as topic>
          <div class="media">
            <div class="media-left">
              <a href="/user/${topic.user.username!}"><img src="${topic.user.avatar}" class="avatar" alt=""></a>
            </div>
            <div class="media-body">
              <div class="title">
                <a href="/topic/${topic.id?c}">${topic.title!?html}</a>
              </div>
              <p class="gray">
                <#if topic.top == true>
                  <span class="label label-primary">置顶</span>
                <#elseif topic.good == true>
                  <span class="label label-success">精华</span>
                <#else>
                  <a href="/go/${topic.node.value!}">${topic.node.name!}</a>
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
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/go/${value}"/>
      </div>
    </@node_topics_tag>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#if sec.isAuthenticated()>

    <#--auth user info-->
      <#include "../components/user_info.ftl"/>
      <@user_info/>

    <#--attendance btn-->
      <#include "../components/attendance.ftl"/>
      <@attendance />
    <#else>
      <#include "../components/welcome.ftl"/>
      <@welcome/>
    </#if>

    <#include "../components/score.ftl"/>
    <@score p=1 limit=10/>
  </div>
</div>
</@html>