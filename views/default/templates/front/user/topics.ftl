<#include "../common/layout.ftl"/>
<@html page_title="${username}创建的话题" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <#include "../components/user_topics.ftl"/>
    <@user_topics username=username p=p isPaginate=true/>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>