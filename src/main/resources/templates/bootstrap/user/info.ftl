<#include "../common/layout.ftl"/>
<@html page_title="${pageTitle!}" page_tab="user">
<div class="row">
  <div class="col-md-9">
    <#if currentUser??>
      <div class="panel panel-default">
        <div class="panel-body">
          <div class="media">
            <div class="media-left">
              <img src="${currentUser.avatar}" class="avatar-lg" alt=""/>
            </div>
            <div class="media-body">
              <h3 style="margin-top: 0">${currentUser.username!}</h3>
              <#if currentUser.signature??>
                <p><i class="gray">${currentUser.signature!}</i></p>
              </#if>
              <div>收藏话题: <a href="/user/${currentUser.username}/collects">${collectCount!0}</a></div>
              <#if currentUser.url??>
                <div>主页: <a href="${currentUser.url!}" target="_blank">${currentUser.url!}</a></div>
              </#if>
              <div>入驻时间: ${currentUser.formatDate(currentUser.inTime)}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">${currentUser.username}创建的话题</div>
        <#if topicPage.getTotalElements() == 0>
          <div class="panel-body">
            暂无话题
          </div>
        <#else>
          <div class="panel-body">
            <#include "../components/user_topics.ftl"/>
            <@user_topics topics=topicPage.getContent()/>
          </div>
          <div class="panel-footer">
            <a href="/user/${currentUser.username}/topics">${currentUser.username}更多话题&gt;&gt;</a>
          </div>
        </#if>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">${currentUser.username}回复的话题</div>
        <#if topicPage.getTotalElements() == 0>
          <div class="panel-body">
            暂无回复
          </div>
        <#else>
          <#include "../components/user_replies.ftl"/>
          <@user_replies replies=replyPage.getContent()/>
          <div class="panel-footer">
            <a href="/user/${currentUser.username}/replies">${currentUser.username}更多回复&gt;&gt;</a>
          </div>
        </#if>
      </div>
    <#else>
      <div class="panel panel-default">
        <div class="panel-body">
          <p>用户未找到</p>
          <a href="/">返回首页</a>
        </div>
      </div>
    </#if>
  </div>
  <div class="col-md-3 hidden-sm hidden-xs">
    <#if userinfo??>
                <#include "../components/user_info.ftl">
                <@info/>
                <#include "../components/create_topic.ftl">
                <@create_topic/>
            </#if>
  </div>
</div>
</@html>