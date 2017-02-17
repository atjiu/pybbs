<#include "../common/layout.ftl"/>
<@html page_tab="user">
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
              <div><@spring.message "site.panel.body.collection"/>: <a href="/user/${currentUser.username}/collects">${collectCount!0}</a></div>
              <#if currentUser.url??>
                <div><@spring.message "site.panel.header.home"/>: <a href="${currentUser.url!}" target="_blank">${currentUser.url!}</a></div>
              </#if>
              <div><@spring.message "site.panel.body.signUpTime"/>: ${currentUser.formatDate(currentUser.inTime)}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">${currentUser.username} <@spring.message "site.page.user.topic"/></div>
        <#if topicPage.getTotalElements() == 0>
          <div class="panel-body">
            <@spring.message "site.panel.body.noTopic"/>
          </div>
        <#else>
          <div class="panel-body">
            <#include "../components/user_topics.ftl"/>
            <@user_topics topics=topicPage.getContent()/>
          </div>
          <div class="panel-footer">
            <a href="/user/${currentUser.username}/topics"><@spring.message "site.panel.body.more"/> &gt;&gt;</a>
          </div>
        </#if>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">${currentUser.username} <@spring.message "site.page.user.comment"/></div>
        <#if replyPage.getTotalElements() == 0>
          <div class="panel-body">
            <@spring.message "site.panel.body.noComment"/>
          </div>
        <#else>
          <#include "../components/user_replies.ftl"/>
          <@user_replies replies=replyPage.getContent()/>
          <div class="panel-footer">
            <a href="/user/${currentUser.username}/replies"><@spring.message "site.panel.body.more"/> &gt;&gt;</a>
          </div>
        </#if>
      </div>
    <#else>
      <div class="panel panel-default">
        <div class="panel-body">
          <p><@spring.message "site.page.user.notExist"/></p>
          <a href="/"><@spring.message "site.panel.body.backHome"/></a>
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