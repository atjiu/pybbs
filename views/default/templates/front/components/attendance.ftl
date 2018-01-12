<#macro attendance >
  <#if sec.isAuthenticated() && _attendance != "1">
  <div class="panel panel-default">
    <button style="border: 0" class="btn btn-default btn-block" onclick="attendance(this)">签到</button>
  </div>
  <script>
    function attendance(e) {
      $.ajax({
        url: "/common/attendance",
        async: true,
        cache: false,
        type: "get",
        dataType: "json",
        success: function (data) {
          if (data.code === 200) {
            $(e).text("你领取了 " + data.detail + " 积分");
          } else {
            $(e).text(data.description);
          }
          setTimeout(function () {
            $(e).parent().fadeOut();
          }, 1500)
        }
      });
    }
  </script>
  </#if>
</#macro>