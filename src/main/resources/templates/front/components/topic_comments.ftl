<#if comments?size == 0>
  <div class="nocomment-tip">目前还没有评论</div>
<#else>
  <div class="panel panel-default">
    <div class="panel-heading">共 ${comments?size} 条评论</div>
    <div class="panel-body">
      <#list comments as comment>
        <div class="media" id="comment${comment.id}">
          <div class="media-body">
            <div class="media-heading gray">
              <a href="/user/${comment.username}"><img src="${comment.avatar}" class="avatar avatar-sm" alt=""/></a>
              <a href="/user/${comment.username!}">${comment.username!} </a>
              <#if topicUser?? && topicUser.id == comment.userId>
                <span class="text-success">[楼主]</span>
              </#if>
              ${model.formatDate(comment.in_time)}
              <span class="pull-right">
              <#if _user??>
                <#if _user.id != comment.userId>
                  <#--<i id="up_icon_${comment.id}" class="fa fa-chevron-up
                    <#if comment.up_ids?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
                     onclick="vote('${comment.id}', 'UP')"></i>
                  <i id="down_icon_${comment.id}" class="fa fa-chevron-down
                    <#if comment.down_ids?split(",")?seq_contains('${user.id}')> up-down-enable <#else> up-down-disable </#if>"
                     onclick="vote('${comment.id}', 'DOWN')"></i>
                  <span id="up_down_vote_count_${comment.id}">${comment.up - comment.down}</span>-->
                <#else>
                  <a href="/comment/edit/${comment.id}"><span class="glyphicon glyphicon-edit"></span></a>
                  <a href="javascript:;" onclick="deleteComment(${comment.id})"><span
                      class="glyphicon glyphicon-trash"></span></a>
                </#if>
                <a href="javascript:commentThis('${comment.username}', '${comment.id}');"><span
                    class="glyphicon glyphicon-comment"></span></a>
              <#else>
                <#--<i id="up_icon_${comment.id}" class="fa fa-chevron-up up-down-disable" onclick="vote('${comment.id}', 'UP')"></i>
                <i id="down_icon_${comment.id}" class="fa fa-chevron-down up-down-disable" onclick="vote('${comment.id}', 'DOWN')"></i>
                <span id="up_down_vote_count_${comment.id}">${comment.up - comment.down}</span>-->
              </#if>
              </span>
            </div>
            <div class="comment-detail-content clearfix">${model.formatContent(comment.content)}</div>
          </div>
        </div>
        <#if comment?has_next>
          <div class="divide"></div>
        </#if>
      </#list>
    </div>
  </div>
</#if>
