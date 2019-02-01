<#include "../layout/layout.ftl"/>
<@html page_title="${username}收藏的话题" page_tab="">
  用户 <a href="/user/${username}">${username}</a> / 收藏的话题
  <#include "../components/user_collects.ftl"/>
  <@user_collects username=username pageNo=pageNo pageSize=site.page_size paginate=true/>
</@html>
