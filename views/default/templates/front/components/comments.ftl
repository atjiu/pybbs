<#macro comment topic_user=null id=id>
  <@comments_tag id=id>
  <#list comments as map>
    <#if map_index != 0 && map.comment.layer == 1>
        <div class="divide" style="padding-bottom: 15px;"></div>
    </#if>
    <div class="media media-comment" style="margin-left: ${(map.comment.layer - 1) * 20}px;" id="comment${map.comment.id}">
      <div class="media-body">
        <div class="media-heading gray">
          <a href="/user/${map.user.username}"><img src="${map.user.avatar}" class="avatar-sm" alt=""/></a>
          <a href="/user/${map.user.username!}">${map.user.username!} </a>
          <#if topic_user?? && topic_user.username == map.user.username>
            <span class="text-success">[楼主]</span>
          </#if>
          ${model.formatDate(map.comment.inTime)}
          <#if user??>
            <span class="pull-right">
              <#if user.id != map.comment.userId>
                <i class="fa fa-chevron-up
                  <#if map.comment.upIds?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
                      onclick="vote(this, '${map.comment.id}', 'up')"></i>
                <i class="fa fa-chevron-down
                  <#if map.comment.downIds?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
                      onclick="vote(this, '${map.comment.id}', 'down')"></i>
                <span id="up_down_vote_count_${map.comment.id}">${map.comment.up - map.comment.down}</span>
              <#else>
                <a href="/comment/edit?id=${map.comment.id}"><span class="glyphicon glyphicon-edit"></span></a>
                <a href="javascript:if(confirm('确定要删除吗？'))location.href='/comment/delete?id=${map.comment.id!}'"><span
                    class="glyphicon glyphicon-trash"></span></a>
              </#if>
              <a href="javascript:commentThis('${map.user.username}', '${map.comment.id}');"><span
                  class="glyphicon glyphicon-comment"></span></a>
            </span>
          </#if>
        </div>
        <div class="show_big_image comment-detail-content clearfix">${map.comment.content!}</div>
      </div>
    </div>
  </#list>
  <script>
    function vote(dom, id, action) {
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
            if ($(dom).hasClass("up-down-enable")) {
              $(dom).removeClass("up-down-enable");
              $(dom).addClass("up-down-disable");
            } else if ($(dom).hasClass("up-down-disable")) {
              $(dom).removeClass("up-down-disable");
              $(dom).addClass("up-down-enable");
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