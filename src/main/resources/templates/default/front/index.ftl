<#include "./common/layout.ftl">
<@html page_title="首页 - ${site.name!}">
<div class="row">
  <div class="col-md-9">
  <#--tab info-->
    <div class="panel panel-default">
      <div class="panel-heading">
        <ul class="nav nav-pills">
          <li <#if tab == '全部'>class="active"</#if>><a href="/?tab=全部">全部</a></li>
          <li <#if tab == '精华'>class="active"</#if>><a href="/?tab=精华">精华</a></li>
          <li <#if tab == '等待回复'>class="active"</#if>><a href="/?tab=等待回复">等待回复</a></li>
          <li class="dropdown <#if tab != '全部' && tab != '精华' && tab != '等待回复'>active</#if>"
              style="margin-right: 8px;">
            <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)" data-target="#">
            ${sectionName!} <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <#list sections as section>
                <li>
                  <a href="/?tab=${section.name!}">${section.name!}</a>
                </li>
              </#list>
            </ul>
          </li>
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

    <#--create topic btn-->
      <#include "components/create_topic.ftl"/>
      <@create_topic/>
    <#else>
      <#include "./components/welcome.ftl"/>
      <@welcome/>
    </#if>

    <#include "./components/score.ftl"/>
    <@score p=1 limit=10/>
  </div>
</div>
</@html>