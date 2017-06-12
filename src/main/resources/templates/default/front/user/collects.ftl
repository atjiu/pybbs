<#include "../common/layout.ftl"/>
<@html page_title="${username}收藏的话题" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <#include "../components/user_collects.ftl"/>
    <@user_collect username=username p=p/>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>