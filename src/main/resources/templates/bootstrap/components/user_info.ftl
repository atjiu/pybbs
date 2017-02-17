<#macro info>
<div class="panel panel-default">
  <div class="panel-heading">
    <@spring.message "site.panel.body.profile"/>
  </div>
  <div class="panel-body">
    <div class="media">
      <div class="media-left">
        <a href="/user/${user.username!}">
          <img src="${user.avatar!}" title="${user.nickname!}" class="avatar"/>
        </a>
      </div>
      <div class="media-body">
        <div class="media-heading">
          <a href="/user/${user.username!}">${user.username!}</a>
        </div>
      </div>
      <#if user.signature?? && user.signature != "">
        <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
          <i>“ ${user.signature!} ” </i>
        </div>
      </#if>
    </div>
  </div>
</div>
</#macro>