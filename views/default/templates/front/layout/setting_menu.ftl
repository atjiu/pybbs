<#macro setting_menu setting_menu_tab>
<div class="panel panel-default">
  <div class="list-group">
    <a data-pjax href="/user/setting/profile" class="list-group-item <#if setting_menu_tab == 'profile'>active</#if>">个人设置</a>
    <a data-pjax href="/user/setting/changeAvatar" class="list-group-item <#if setting_menu_tab == 'changeAvatar'>active</#if>">修改头像</a>
    <a data-pjax href="/user/setting/changePassword" class="list-group-item <#if setting_menu_tab == 'changePassword'>active</#if>">修改密码</a>
    <a data-pjax href="/user/setting/accessToken" class="list-group-item <#if setting_menu_tab == 'accessToken'>active</#if>">用户令牌</a>
    <a data-pjax href="/user/setting/log" class="list-group-item <#if setting_menu_tab == 'log'>active</#if>">日志记录</a>
  </div>
</div>
</#macro>