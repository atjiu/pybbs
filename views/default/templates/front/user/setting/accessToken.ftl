<#include "../../common/layout.ftl"/>
<@html page_title="用户令牌" page_tab="setting">
<div class="row">

  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../common/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="accessToken"/>
  </div>

  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">用户令牌</div>
      <div class="panel-body">
        <p>Token: ${user.token!} <a href="/user/refreshToken" class="btn btn-xs btn-danger">刷新Token</a></p>
        <p id="qrcode"></p>
      </div>
    </div>
  </div>
</div>
<script src="//cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<script type="text/javascript">
  $('#qrcode').qrcode("${user.token!}");
</script>
</@html>