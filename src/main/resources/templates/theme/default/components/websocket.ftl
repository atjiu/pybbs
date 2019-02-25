<script>
  <#if _user??>
    <#if site.websocket == "1">
      var documentTitle = document.title;
      var socket = io.connect('ws://${site.websocket_host}:${site.websocket_port}');
      socket.on('connect', function () {
        // 连接成功后，发送本地用户与socket绑定事件
        socket.emit('bind', {
          "username": "${_user.username}",
          "userId": "${_user.id}"
        });
      });
      // 接收消息通知的信息
      socket.on('notifications', function (data) {
        toast(data, "success");
      });
      // 接收未读消息的数量
      socket.on('notification_notread', function (data) {
        var n_count = $("#n_count");
        var nh_count = $("#nh_count");
        if (n_count) n_count.text(parseInt(n_count.text()) + data);
        var notReadTotalCount = 0;
        if (nh_count.text().length === 0) {
          notReadTotalCount = data;
        } else {
          notReadTotalCount = parseInt(nh_count.text()) + data;
        }
        if (notReadTotalCount > 0) {
          nh_count.text(notReadTotalCount);
          document.title = "(" + notReadTotalCount + ") " + documentTitle;
        }
      });
    <#else>
      var title = document.title;

      function notificationCount() {
        $.ajax({
          url: '/api/notification/notRead',
          cache: false,
          async: false,
          type: 'get',
          dataType: 'json',
          contentType: 'application/json',
          headers: {
            'token': '${_user.token}'
          },
          success: function (data) {
            if (data.code === 200 && data.detail > 0) {
              $("#n_count").text(data.detail);
              document.title = "(" + data.detail + ") " + title;
            }
          }
        })
      }

      notificationCount();
      // setInterval(function () {
      //   notificationCount();
      // }, 120000);
    </#if>
  </#if>
</script>
