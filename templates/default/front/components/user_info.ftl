<#macro user_info username="" text="个人信息">
  <@user_tag username=username>
  <div class="panel panel-default">
    <div class="panel-heading">
      ${text}
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
            <p>积分：<a href="/top100">${user.score!0}</a></p>
          </div>
        </div>
        <#if user.signature?? && user.signature != "">
          <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
            <i>“ ${user.signature!} ” </i>
          </div>
        </#if>
        <#if text=="个人信息">
          <div class="mar-bot-10"></div>
          <a href="/topic/create" style="text-decoration: underline"><span class="glyphicon glyphicon-pencil"></span><i>发布话题</i></a>
        </#if>
      </div>
    </div>
  </div>
  </@user_tag>
</#macro>