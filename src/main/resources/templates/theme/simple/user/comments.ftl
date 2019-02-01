<#include "../layout/layout.ftl"/>
<@html page_title="${username}评论的话题" page_tab="">
  用户 <a href="/user/${username}">${username}</a> / 评论的话题
  <#include "../components/user_comments.ftl"/>
  <@user_comments username=username pageNo=pageNo pageSize=site.page_size paginate=true/>
</@html>
