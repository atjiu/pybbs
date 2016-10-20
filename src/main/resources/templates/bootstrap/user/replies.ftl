<#include "../common/layout.ftl"/>
<@html page_title="${currentUser.username}回复的话题" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
      ${currentUser.username}回复的话题
      </div>
      <#include "../components/user_replies.ftl"/>
      <@user_replies replies=page.getContent()/>
      <div class="panel-body" style="padding: 0 15px;">
        <#include "../components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${currentUser.username}/replies" urlParas="" showdivide="no"/>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>