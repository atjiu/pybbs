<#include "layout/" + layoutName>
<@html page_title="首页 - ${site.name!}" page_tab="index">
<div class="row">
  <div class="col-md-9">
  <#--tab info-->
    <div class="panel panel-default">
      <div class="panel-heading">
        <ul class="nav nav-pills">
          <li <#if !tab?? || tab == ''>class="active"</#if>><a data-pjax href="/">默认</a></li>
          <li <#if tab?? && tab == 'good'>class="active"</#if>><a data-pjax href="/?tab=good">精华</a></li>
          <li <#if tab?? && tab == 'newest'>class="active"</#if>><a data-pjax href="/?tab=newest">最新</a></li>
          <li <#if tab?? && tab == 'noanswer'>class="active"</#if>><a data-pjax href="/?tab=noanswer">等待评论</a></li>
        </ul>
      </div>

    <#--topic list-->
      <@topics_tag p=p tab=tab>
        <div class="panel-body paginate-bot">
          <#include "./components/topics.ftl"/>
          <@topics topics=page.content/>
          <#include "./components/paginate.ftl"/>
          <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/" urlParas="&tab=${tab!}"/>
        </div>
      </@topics_tag>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#if user??>
      <#include "./components/user_info.ftl"/>
      <@user_info/>
    <#else>
      <#include "./components/welcome.ftl"/>
      <@welcome/>
    </#if>
    <#include "./components/reputation.ftl"/>
    <@reputation p=1 limit=10/>
  </div>
</div>
</@html>