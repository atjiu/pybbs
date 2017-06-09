<#macro reply replies>
  <#list replies as reply>
  <div class="media media-reply <#if reply.upDown &gt;= 3> reply-highlight</#if>" id="reply${reply.id}">
    <div class="media-left">
      <a href="/user/${reply.user.username}"><img src="${reply.user.avatar}" class="avatar" alt=""/></a>
    </div>
    <div class="media-body reply-content">
      <div class="media-heading gray">
        <a href="/user/${reply.user.username!}">${reply.user.username!} </a>
      ${reply.formatDate(reply.inTime)}
        <#if user?? && user.block == false>
          <span class="pull-right">
            <#if reply.isUp(user.id, reply.upIds) == true>
              <a id="reply_up_${reply.id}" href="javascript:replyUp('${reply.id}')">
                <span class="glyphicon glyphicon-thumbs-up"></span>
              </a>
            <#else>
              <a id="reply_up_${reply.id}" href="javascript:replyUp('${reply.id}')">
                <span class="glyphicon glyphicon-thumbs-up"></span>
              </a>
            </#if>
            <#if reply.isDown(user.id, reply.downIds) == true>
              <a id="reply_down_${reply.id}" href="javascript:replyDown('${reply.id}')">
                <span class="glyphicon glyphicon-thumbs-down"></span>
              </a>
            <#else>
              <a id="reply_down_${reply.id}" href="javascript:replyDown('${reply.id}')">
                <span class="glyphicon glyphicon-thumbs-down"></span>
              </a>
            </#if>
            <span>${reply.upDown}</span>
            <#if sec.allGranted("reply:edit") || user.id == reply.user.id>
              <a href="/reply/${reply.id}/edit"><span class="glyphicon glyphicon-edit"></span></a>
            </#if>
            <#if sec.allGranted("reply:delete") || user.id == reply.user.id>
              <a href="javascript:if(confirm('确定要删除吗？'))location.href='/reply/${reply.id!}/delete'"><span class="glyphicon glyphicon-trash"></span></a>
            </#if>
            <a href="javascript:replythis('${reply.user.username}');"><span class="glyphicon glyphicon-comment"></span></a>
          </span>
        </#if>
      </div>
      ${reply.marked(reply.content)}
    </div>
  </div>
    <#if reply_has_next>
      <div class="divide"></div>
    </#if>
  </#list>

</#macro>