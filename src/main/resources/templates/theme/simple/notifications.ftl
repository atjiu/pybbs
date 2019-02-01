<#include "layout/layout.ftl"/>
<@html page_title="通知" page_tab="notification">
  <div>
    <b>
      新消息
      <a id="markRead" href="javascript:markRead()" class="pull-right" style="display: none">标记已读</a>
    </b>
    <hr>
    <#include "components/notification.ftl"/>
    <@notification userId=_user.id read=0 limit=-1/>
  </div>
  <div style="margin-top: 10px;">
    <b>已读消息</b>
    <hr>
    <#include "components/notification.ftl"/>
    <@notification userId=_user.id read=1 limit=20/>
  </div>
  <script>
    $(function () {
      if ($(".notification_0").length > 0) {
        $("#markRead").show();
      }
    });
    function markRead() {
      $.get('/api/notification/markRead?token=${_user.token}', function (data) {
        if (data.code === 200) {
          window.location.reload();
        }
      })
    }
  </script>
</@html>
