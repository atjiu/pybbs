<#include "./common/layout.ftl">
<@html page_title="首页 - ${site.name!}" page_tab="index">
<div class="row">
  <div class="col-md-9">
  <#--tab info-->
    <div class="panel panel-default">
      <div class="panel-heading">
        <ul class="nav nav-pills">
          <li <#if !tab?? || tab == ''>class="active"</#if>><a href="/">默认</a></li>
          <li <#if tab?? && tab == 'good'>class="active"</#if>><a href="/?tab=good">精华</a></li>
          <li <#if tab?? && tab == 'newest'>class="active"</#if>><a href="/?tab=newest">最新</a></li>
          <li <#if tab?? && tab == 'noanswer'>class="active"</#if>><a href="/?tab=noanswer">等待回复</a></li>
          <li><a href="javascript:;">节点</a></li>
        </ul>
      </div>

    <#--topic list-->
      <@topics_tag p=p tab=tab>
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
                    <a href="/topics/${topic.node.value!}">${topic.node.name!}</a>
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
          <#include "./components/paginate.ftl"/>
          <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/" urlParas="&tab=${tab!}"/>
        </div>
      </@topics_tag>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#if sec.isAuthenticated()>

    <#--auth user info-->
      <#include "./components/user_info.ftl"/>
      <@user_info/>

    <#--attendance btn-->
      <#include "components/attendance.ftl"/>
      <@attendance />
    <#else>
      <#include "./components/welcome.ftl"/>
      <@welcome/>
    </#if>

    <#include "./components/score.ftl"/>
    <@score p=1 limit=10/>
  </div>
</div>
</@html>