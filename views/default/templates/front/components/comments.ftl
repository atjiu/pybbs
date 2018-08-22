<#macro comment topic_user=null id=id>
  <@comments_tag id=id>
  <#list comments as comment>
    <#if comment_index != 0 && comment.layer == 1>
        <div class="divide" style="padding-bottom: 15px;"></div>
    </#if>
    <div class="media media-comment" style="margin-left: ${(comment.layer - 1) * 20}px;" id="comment${comment.id}">
      <div class="media-body">
        <div class="media-heading gray">
          <a data-pjax href="/user/${comment.username}"><img src="${comment.avatar}" class="avatar-sm" alt=""/></a>
          <a data-pjax href="/user/${comment.username!}">${comment.username!} </a>
          <#if topic_user?? && topic_user.username == comment.username>
            <span class="text-success">[楼主]</span>
          </#if>
          ${model.formatDate(comment.in_time)}
          <span class="pull-right">
            <#if user??>
              <#if user.id != comment.user_id>
                <i id="up_icon_${comment.id}" class="fa fa-chevron-up
                  <#if comment.up_ids?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
                      onclick="vote('${comment.id}', 'UP')"></i>
                <i id="down_icon_${comment.id}" class="fa fa-chevron-down
                  <#if comment.down_ids?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
                      onclick="vote('${comment.id}', 'DOWN')"></i>
                <span id="up_down_vote_count_${comment.id}">${comment.up - comment.down}</span>
              <#else>
                <a data-pjax href="/comment/edit?id=${comment.id}"><span class="glyphicon glyphicon-edit"></span></a>
                <a data-pjax href="javascript:if(confirm('确定要删除吗？'))location.href='/comment/delete?id=${comment.id!}'"><span
                    class="glyphicon glyphicon-trash"></span></a>
              </#if>
              <a href="javascript:commentThis('${comment.username}', '${comment.id}');"><span
                  class="glyphicon glyphicon-comment"></span></a>
            <#else>
              <i id="up_icon_${comment.id}" class="fa fa-chevron-up up-down-disable" onclick="vote('${comment.id}', 'UP')"></i>
              <i id="down_icon_${comment.id}" class="fa fa-chevron-down up-down-disable" onclick="vote('${comment.id}', 'DOWN')"></i>
              <span id="up_down_vote_count_${comment.id}">${comment.up - comment.down}</span>
            </#if>
          </span>
        </div>
        <div class="show_big_image comment-detail-content clearfix">${comment.content!}</div>
      </div>
    </div>
  </#list>
  <script>
    function vote(id, action) {
      $.ajax({
        url: '/api/comment/' + id + '/vote',
        async: true,
        cache: false,
        type: "get",
        dataType: "json",
        data: {
          action: action
        },
        success: function (data) {
          if (data.code === 200) {
            $("#up_down_vote_count_" + id).text(data.detail.vote);
            var upIcon = $("#up_icon_" + data.detail.commentId);
            var downIcon = $("#down_icon_" + data.detail.commentId);
            upIcon.removeClass("up-down-enable");
            upIcon.removeClass("up-down-disable");
            downIcon.removeClass("up-down-enable");
            downIcon.removeClass("up-down-disable");
            if(data.detail.isUp) {
              upIcon.addClass("up-down-enable");
              downIcon.addClass("up-down-disable");
            } else if(data.detail.isDown) {
              downIcon.addClass("up-down-enable");
              upIcon.addClass("up-down-disable");
            }
          } else {
            toast(data.description);
          }
        }
      });
    }
  </script>
  </@comments_tag>
</#macro>