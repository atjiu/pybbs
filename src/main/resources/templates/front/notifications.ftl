<#include "./layout/layout.ftl"/>
<@html page_title="通知" page_tab="notification">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">新消息</div>
      <div class="panel-body">
        <#include "./components/notification.ftl"/>
        <@notification userId=_user.id read=0 limit=-1/>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">已读消息</div>
      <div class="panel-body">
        <#include "./components/notification.ftl"/>
        <@notification userId=_user.id read=1 limit=20/>
      </div>
    </div>
  </div>
  <div class="col-md-3">
    <#if _user??>
      <#include "./components/user_info.ftl"/>
    <#else>
      <#include "./components/welcome.ftl"/>
    </#if>
  </div>
</div>
</@html>
