<#macro user_info username="" text="self">
  <@user_tag username=username>
  <div class="panel panel-default">
    <div class="panel-body">
      <div class="media">
        <div class="media-left">
          <a data-pjax href="/user/${user.username!}">
            <img src="${user.avatar!}" title="${user.nickname!}" class="avatar"/>
          </a>
        </div>
        <div class="media-body">
          <div class="media-heading">
            <a data-pjax href="/user/${user.username!}">${user.username!}</a>
            <div style="color: #7A7A7A; font-size: 12px; margin-top:5px;">
              <i>${(user.bio!"这家伙很懒，什么都没有留下")?html}</i>
            </div>
          </div>
        </div>
        <div style="margin-top: 15px;">
          <a href="/topic/create" style="text-decoration: underline"><span class="glyphicon glyphicon-pencil"></span><i>发布话题</i></a>
        </div>
      </div>
    </div>
    <div class="panel-footer" style="background-color: white">
      <div class="row">
        <span class="col-md-6"><a data-pjax href="/notification/list"><span id="n_count">0</span> 条未读消息</a></span>
        <span class="col-md-6 text-right">声望：<a data-pjax href="/top100">${user.reputation!0}</a></span>
      <#--如果没有开启socket通知，则继续使用轮询的方式-->
        <#if !site.socketNotification>
          <script>
            var title = document.title;

            function notificationCount() {
              $.ajax({
                url: "/api/notification/notRead",
                async: true,
                cache: false,
                type: "get",
                dataType: "json",
                success: function (data) {
                  if (data.code === 200 && data.detail > 0) {
                    $("#n_count").text(data.detail);
                    document.title = "(" + data.detail + ") " + title;
                  }
                }
              });
            }

            notificationCount();
            setInterval(function () {
              notificationCount();
            }, 120000);
          </script>
        </#if>
      </div>
    </div>
  </div>
  </@user_tag>
</#macro>