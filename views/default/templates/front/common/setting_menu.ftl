<#macro setting_menu setting_menu_tab>
<div class="panel panel-default">
  <div class="list-group">
    <a href="/user/profile" class="list-group-item <#if setting_menu_tab == 'profile'>active</#if>">个人设置</a>
    <a href="/user/changeAvatar" class="list-group-item <#if setting_menu_tab == 'changeAvatar'>active</#if>">修改头像</a>
    <a href="/user/changePassword" class="list-group-item <#if setting_menu_tab == 'changePassword'>active</#if>">修改密码</a>
    <a href="/user/accessToken" class="list-group-item <#if setting_menu_tab == 'accessToken'>active</#if>">用户令牌</a>
    <a href="/user/space" class="list-group-item <#if setting_menu_tab == 'space'>active</#if>">个人空间</a>
    <a href="/user/scoreLog" class="list-group-item <#if setting_menu_tab == 'scoreLog'>active</#if>">积分记录</a>
  </div>
</div>
</#macro>