<#include "../layout/" + layoutName/>
<@html page_title="${username}评论的话题" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <#include "../components/user_comments.ftl"/>
    <@user_comments username=username p=p isPaginate=true/>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>