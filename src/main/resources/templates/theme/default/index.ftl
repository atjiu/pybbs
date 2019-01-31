<#include "layout/layout.ftl"/>
<@html page_title="首页" page_tab="index">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-info">
      <div class="panel-heading">
        <ul class="nav nav-pills">
          <li <#if tab=="all">class="active"</#if>><a href="/?tab=all">全部</a></li>
          <li <#if tab=="good">class="active"</#if>><a href="/?tab=good">精华</a></li>
          <li <#if tab=="hot">class="active"</#if>><a href="/?tab=hot">最热</a></li>
          <li <#if tab=="newest">class="active"</#if>><a href="/?tab=newest">最新</a></li>
          <li <#if tab=="noanswer">class="active"</#if>><a href="/?tab=noanswer">无人问津</a></li>
        </ul>
      </div>
      <div class="panel-body">
        <@tag_topics pageNo=pageNo tab=tab>
          <#include "components/topics.ftl"/>
          <@topics page=page/>

          <#include "components/paginate.ftl"/>
          <@paginate currentPage=page.current totalPage=page.pages actionUrl="/" urlParas="&tab=${tab!}"/>
        </@tag_topics>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-xs">
    <#if _user??>
      <#include "components/user_info.ftl"/>
    <#else>
      <#include "components/welcome.ftl"/>
    </#if>
    <#include "components/score.ftl"/>
    <@score limit=10/>
  </div>
</div>
</@html>
