<#include "../layout/layout.ftl"/>
<@html page_title="${user.username}收藏的话题" page_tab="">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">${user.username}评论的话题</div>
      <div class="panel-body">
        <#if collects.total == 0>
          <div class="panel-body">
            暂无评论
          </div>
        <#else>
          <#include "../components/topics.ftl"/>
          <@topics page=collects/>
        </#if>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <#if _user??>
      <#include "../components/user_info.ftl"/>
    <#else>
      <#include "../components/welcome.ftl"/>
    </#if>
    <#include "../components/score.ftl"/>
    <@score limit=10/>
  </div>
</div>
</@html>
