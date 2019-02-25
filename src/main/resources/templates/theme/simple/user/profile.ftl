<#include "../layout/layout.ftl"/>
<@html page_title=username + " 的个人主页" page_tab="user">
  <div class="user">
    <div class="left">
      <img src="${user.avatar!}" width="100%" alt="">
      <div style="margin-top: 10px;">
        <div style="font-size: 18px;">${user.username}</div>
        <ul style="font-size: 14px;">
          <li>积分: <a href="/top100">${user.score!0}</a></li>
          <li>收藏话题: <a href="/user/${user.username}/collects">${collectCount!0}</a></li>
          <li>入驻时间: ${model.formatDate(user.inTime)}</li>
          <#if user.email?? && user.email != "">
            <li><a href="mailto:${user.email}">${user.email}</a></li>
          </#if>
          <#if user.website?? && user.website != "">
            <li><a href="${user.website}" target="_blank">${user.website}</a></li>
          </#if>
          <#if githubLogin?? && user.githubLogin != "">
            <li>Github: <a href="https://github.com/${githubLogin}" target="_blank">${githubLogin}</a></li>
          </#if>
          <#if user.bio?? && user.bio != "">
            <li><i>${user.bio}</i></li>
          </#if>
        </ul>
      </div>
    </div>
    <div class="right">
      <div>
        <b>${username} 近期的话题</b>
        <a href="/user/${username}/topics" class="pull-right">查看更多</a>
        <hr>
        <#include "../components/user_topics.ftl"/>
        <@user_topics username=username pageNo=1 pageSize=10/>
      </div>
      <div>
        <b>${username} 参与的评论</b>
        <a href="/user/${username}/comments" class="pull-right">查看更多</a>
        <hr>
        <#include "../components/user_comments.ftl"/>
        <@user_comments username=username pageNo=1 pageSize=10/>
      </div>
    </div>
  </div>
</@html>
