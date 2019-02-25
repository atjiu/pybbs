<#include "layout/layout.ftl"/>
<@html page_title="通知" page_tab="notification">
<div class="row">
  <div class="col-md-9">
    <div class="panel panel-info">
      <div class="panel-heading">
        新消息
        <a id="markRead" href="javascript:markRead()" class="pull-right" style="display: none">标记已读</a>
      </div>
      <div class="panel-body">
        <#include "components/notification.ftl"/>
        <@notification userId=_user.id read=0 limit=-1/>
      </div>
    </div>
    <div class="panel panel-info">
      <div class="panel-heading">已读消息</div>
      <div class="panel-body">
        <#include "components/notification.ftl"/>
        <@notification userId=_user.id read=1 limit=20/>
      </div>
    </div>
  </div>
  <div class="col-md-3 hidden-xs">
    <#if _user??>
      <#include "components/user_info.ftl"/>
    <#else>
      <#include "components/welcome.ftl"/>
    </#if>
  </div>
</div>
  <script>
    $(function () {
      if ($(".notification_0").length > 0) {
        $("#markRead").show();
      }
    });
    function markRead() {
      $.ajax({
        url: '/api/notification/markRead',
        cache: false,
        async: false,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        headers: {
          'token': '${_user.token}'
        },
        success: function (data) {
          if (data.code === 200) {
            window.location.reload();
          }
        }
      })
    }
  </script>
</@html>
