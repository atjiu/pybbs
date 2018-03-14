<#macro comment topic_user=null id=id>
  <@replies_tag id=id>
    <#list replies as comment>
    <div class="media media-comment <#if comment.upDown &gt;= 3> comment-highlight</#if>" id="comment${comment.id}">
      <div class="media-left">
        <a href="/user/${comment.user.username}"><img src="${comment.user.avatar}" class="avatar" alt=""/></a>
      </div>
      <div class="media-body comment-content">
        <div class="media-heading gray">
          <a href="/user/${comment.user.username!}">${comment.user.username!} </a>
          <#if topic_user?? && topic_user.username == comment.user.username>
            <span class="text-success">[楼主]</span>
          </#if>
        ${model.formatDate(comment.inTime)}
          <#if sec.isAuthenticated() && !sec.isLock()>
            <span class="pull-right">
              <#if model.isUp(user.id, comment.upIds) == true>
                <span class="glyphicon glyphicon-thumbs-up up-down-enable"
                      onclick="commentUp(this, '${comment.id}')"></span>
              <#else>
                <span class="glyphicon glyphicon-thumbs-up up-down-disable"
                      onclick="commentUp(this, '${comment.id}')"></span>
              </#if>
              <#if model.isDown(user.id, comment.downIds) == true>
                <span class="glyphicon glyphicon-thumbs-down up-down-enable"
                      onclick="commentDown(this, '${comment.id}')"></span>
              <#else>
                <span class="glyphicon glyphicon-thumbs-down up-down-disable"
                      onclick="commentDown(this, '${comment.id}')"></span>
              </#if>
              <span id="up_down_vote_count_${comment.id}">${comment.upDown}</span>
              <#if sec.allGranted("comment:edit")>
                <a href="/admin/comment/${comment.id}/edit"><span class="glyphicon glyphicon-edit"></span></a>
              </#if>
              <#if sec.allGranted("comment:delete")>
                <a href="javascript:if(confirm('确定要删除吗？'))location.href=comment${comment.id!}"><span
                    class="glyphicon glyphicon-trash"></span></a>
              </#if>
              <a href="javascript:commentThis('${comment.user.username}');"><span
                  class="glyphicon glyphicon-comment"></span></a>
            </span>
          </#if>
        </div>
        <div class="show_big_image comment-detail-content">${model.marked(comment.content, true)}</div>
      </div>
    </div>
      <#if comment_has_next>
      <div class="divide"></div>
      </#if>
    </#list>
  <script>
    function commentUp(dom, id) {
      var url;
      if ($(dom).hasClass("up-down-enable")) {
        url = "/comment/" + id + "/cancelUp"
      } else if ($(dom).hasClass("up-down-disable")) {
        url = "/comment/" + id + "/up";
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

    function commentDown(dom, id) {
      var url;
      if ($(dom).hasClass("up-down-enable")) {
        url = "/comment/" + id + "/cancelDown"
      } else if ($(dom).hasClass("up-down-disable")) {
        url = "/comment/" + id + "/down";
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

    function commentThis(author) {
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