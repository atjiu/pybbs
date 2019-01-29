<#include "../layout/layout.ftl"/>
<@html page_title="${username}评论的话题" page_tab="">
<div class="row">
  <div class="col-md-9">
    <#include "../components/user_comments.ftl"/>
    <@user_comments pageNo=pageNo pageSize=site.page_size username=username isPaginate=true/>
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
