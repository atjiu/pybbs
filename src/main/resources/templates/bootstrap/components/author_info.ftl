<#macro info>
<div class="panel panel-default">
  <div class="panel-heading">
    作者
  </div>
  <div class="panel-body">
    <div class="media">
      <div class="media-left">
        <a href="/user/${author.username!}">
          <img src="${author.avatar!}" title="${author.username!}" class="avatar"/>
        </a>
      </div>
      <div class="media-body">
        <div class="media-heading">
          <a href="/user/${author.username!}">${author.username!}</a>
        </div>
      </div>
      <#if author.signature?? && author.signature != "">
        <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
          <i>“ ${author.signature!} ” </i>
        </div>
      </#if>
    </div>
  </div>
</div>
</#macro>