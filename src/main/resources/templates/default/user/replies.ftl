<#include "../common/layout.ftl"/>
<@html page_title="${currentUser.username}回复的话题" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
      ${currentUser.username}回复的话题
      </div>
      <#include "../components/user_replies.ftl"/>
      <@user_replies username=currentUser.username p=p isPaginate=true/>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>