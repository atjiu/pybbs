<#include "../layout/layout.ftl"/>
<@html page_title="${username}收藏的话题" page_tab="">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-info">
      <div class="panel-heading">${username}评论的话题</div>
      <div class="panel-body">
        <@tag_user_collects pageNo=pageNo pageSize=site.page_size username=username>
          <#if collects.total == 0>
            暂无评论
          <#else>
            <#include "../components/topics.ftl"/>
            <@topics page=collects/>
          </#if>
        </@tag_user_collects>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-xs">
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
