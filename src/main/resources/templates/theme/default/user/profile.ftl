<#include "../layout/layout.ftl"/>
<@html page_title=user.username + " 的个人主页" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-info">
      <div class="panel-body">
        <div class="media">
          <div class="media-left">
            <img src="${user.avatar!}" class="avatar avatar-lg" alt="avatar"/>
          </div>
          <div class="media-body">
            <h3 style="margin-top: 0">${user.username!}</h3>
            <p>积分：<a href="/top100">${user.score}</a></p>
            <#if user.bio??>
              <p><i class="gray">${user.bio!}</i></p>
            </#if>
            <p>收藏话题: <a href="/user/${user.username}/collects">${collectCount!0}</a></p>
            <div>入驻时间: ${model.formatDate(user.inTime)}</div>
          </div>
        </div>
      </div>
      <#if user.githubName?? || user.telegramName?? || user.website??>
        <div class="panel-footer" style="background-color: #fff;">
          <#if user.website?? && user.website != "">
            <a href="${user.website!}" class="social_label" target="_blank"><i class="fa fa-home" style="color: #91c330;"></i> ${user.website!}</a>
          </#if>
          <#if user.githubName?? && user.githubName != "">
            <a href="https://github.com/${user.githubName!}" class="social_label" target="_blank"><i class="fa fa-github" style="color: black;"></i> ${user.githubName!}</a>
          </#if>
          <#if user.telegramName?? && user.telegramName != "">
            <a href="https://telegram.me/${user.telegramName!}" class="social_label" target="_blank"><i class="fa fa-telegram" style="color: #61a7e2;"></i> ${user.telegramName!}</a>
          </#if>
        </div>
      </#if>
    </div>
    <#include "../components/user_topics.ftl"/>
    <@user_topics pageNo=1 pageSize=10 username=username isFooter=true/>

    <#include "../components/user_comments.ftl"/>
    <@user_comments pageNo=1 pageSize=10 username=username isFooter=true/>
  </div>
  <div class="col-md-3 hidden-xs">
    <div class="panel panel-info"><p></p></div>
    <#if githubLogin??>
      <#include "../components/github_repos.ftl"/>
    </#if>
  </div>
</div>
</@html>
