<#include "../layout/layout.ftl"/>
<@html page_title="${username}创建的话题" page_tab="">
  用户 <a href="/user/${username}">${username}</a> / 创建的话题
  <#include "../components/user_topics.ftl"/>
  <@user_topics username=username pageNo=pageNo pageSize=site.page_size paginate=true/>
</@html>
