<#include "../common/layout.ftl"/>
<@html page_title="${currentUser.username}创建的话题" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
      ${currentUser.username}创建的话题
      </div>
      <div class="panel-body">
        <#include "../components/user_topics.ftl"/>
        <@user_topics topics=page.getContent()/>
        <#include "../components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/user/${currentUser.username}/topics" urlParas=""/>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>