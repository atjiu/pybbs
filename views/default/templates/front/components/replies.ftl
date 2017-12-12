<#macro reply topic_user=null id=id>
  <@replies_tag id=id>
    <#list replies as reply>
    <div class="media media-reply <#if reply.upDown &gt;= 3> reply-highlight</#if>" id="reply${reply.id}">
      <div class="media-left">
        <a href="/user/${reply.user.username}"><img src="${reply.user.avatar}" class="avatar" alt=""/></a>
      </div>
      <div class="media-body reply-content">
        <div class="media-heading gray">
          <a href="/user/${reply.user.username!}">${reply.user.username!} </a>
          <#if topic_user?? && topic_user.username == reply.user.username>
            <span class="text-success">[楼主]</span>
          </#if>
        ${model.formatDate(reply.inTime)}
          <#if sec.isAuthenticated() && !sec.isLock()>
            <span class="pull-right">
              <#if model.isUp(user.id, reply.upIds) == true>
                <span class="glyphicon glyphicon-thumbs-up up-down-enable"
                      onclick="replyUp(this, '${reply.id}')"></span>
              <#else>
                <span class="glyphicon glyphicon-thumbs-up up-down-disable"
                      onclick="replyUp(this, '${reply.id}')"></span>
              </#if>
              <#if model.isDown(user.id, reply.downIds) == true>
                <span class="glyphicon glyphicon-thumbs-down up-down-enable"
                      onclick="replyDown(this, '${reply.id}')"></span>
              <#else>
                <span class="glyphicon glyphicon-thumbs-down up-down-disable"
                      onclick="replyDown(this, '${reply.id}')"></span>
              </#if>
              <span id="up_down_vote_count_${reply.id}">${reply.upDown}</span>
              <#if sec.allGranted("reply:edit")>
                <a href="/admin/reply/${reply.id}/edit"><span class="glyphicon glyphicon-edit"></span></a>
              </#if>
              <#if sec.allGranted("reply:delete")>
                <a href="javascript:if(confirm('确定要删除吗？'))location.href='/admin/reply/${reply.id!}/delete'"><span
                    class="glyphicon glyphicon-trash"></span></a>
              </#if>
              <a href="javascript:replythis('${reply.user.username}');"><span
                  class="glyphicon glyphicon-comment"></span></a>
            </span>
          </#if>
        </div>
        <div class="show_big_image reply-detail-content">${model.marked(reply.content, true)}</div>
      </div>
    </div>
      <#if reply_has_next>
      <div class="divide"></div>
      </#if>
    </#list>
  <script>
    function replyUp(dom, id) {
      var url;
      if ($(dom).hasClass("up-down-enable")) {
        url = "/reply/" + id + "/cancelUp"
      } else if ($(dom).hasClass("up-down-disable")) {
        url = "/reply/" + id + "/up";
      }
      if (url) {
        $.ajax({
          url: url,
          async: true,
          cache: false,
          type: "get",
          dataType: "json",
          success: function (data) {
            if (data.code === 200) {
              $("#up_down_vote_count_" + id).text(data.detail);
              if ($(dom).hasClass("up-down-enable")) {
                $(dom).removeClass("up-down-enable");
                $(dom).addClass("up-down-disable");
              } else if ($(dom).hasClass("up-down-disable")) {
                $(dom).removeClass("up-down-disable");
                $(dom).addClass("up-down-enable");

                $(".glyphicon-thumbs-down").removeClass("up-down-enable");
                $(".glyphicon-thumbs-down").addClass("up-down-disable");
              }
            } else {
              alert(data.description);
            }
          }
        });
      }
    }

    function replyDown(dom, id) {
      var url;
      if ($(dom).hasClass("up-down-enable")) {
        url = "/reply/" + id + "/cancelDown"
      } else if ($(dom).hasClass("up-down-disable")) {
        url = "/reply/" + id + "/down";
      }
      if (url) {
        $.ajax({
          url: url,
          async: true,
          cache: false,
          type: "get",
          dataType: "json",
          success: function (data) {
            if (data.code === 200) {
              $("#up_down_vote_count_" + id).text(data.detail);
              if ($(dom).hasClass("up-down-enable")) {
                $(dom).removeClass("up-down-enable");
                $(dom).addClass("up-down-disable");
              } else if ($(dom).hasClass("up-down-disable")) {
                $(dom).removeClass("up-down-disable");
                $(dom).addClass("up-down-enable");

                $(".glyphicon-thumbs-up").removeClass("up-down-enable");
                $(".glyphicon-thumbs-up").addClass("up-down-disable");
              }
            } else {
              alert(data.description);
            }
          }
        });
      }
    }

    function replythis(author) {
      var contentVal = $("#content").val();
      if (contentVal.length === 0) {
        contentVal += "@" + author + " ";
      } else {
        contentVal += "\n@" + author + " ";
      }
      $("#content").val(contentVal);
    }
  </script>
  </@replies_tag>
</#macro>