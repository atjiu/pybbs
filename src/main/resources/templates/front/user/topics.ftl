<#include "../layout/layout.ftl"/>
<@html page_title="${user.username}创建的话题" page_tab="">
<div class="row">
  <div class="col-md-9">
    <#include "../components/user_topics.ftl"/>
    <@user_topics isPaginate=true/>
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
