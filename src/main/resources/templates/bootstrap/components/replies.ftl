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
              <button onclick="replyUp('${reply.id}')" type="button" id="reply_up_${reply.id}" class="btn btn-primary btn-xs">
                <span class="glyphicon glyphicon-thumbs-up"></span> <span>${reply.upDown}</span>
              </button>
            <#else>
              <button onclick="replyUp('${reply.id}')" type="button" id="reply_up_${reply.id}" class="btn btn-default btn-xs">
                <span class="glyphicon glyphicon-thumbs-up"></span> <span>${reply.upDown}</span>
              </button>
            </#if>

            <#if reply.isDown(user.id, reply.downIds) == true>
              <button onclick="replyDown('${reply.id}')" type="button" id="reply_down_${reply.id}" class="btn btn-primary btn-xs">
                <span class="glyphicon glyphicon-thumbs-down"></span>
              </button>
            <#else>
              <button onclick="replyDown('${reply.id}')" type="button" id="reply_down_${reply.id}" class="btn btn-default btn-xs">
                <span class="glyphicon glyphicon-thumbs-down"></span>
              </button>
            </#if>

            <#if _roles?seq_contains("reply:edit") || user.id == reply.user.id>
              <a href="/reply/${reply.id}/edit">编辑</a>
            </#if>
            <#if _roles?seq_contains("reply:delete") || user.id == reply.user.id>
              <a href="javascript:if(confirm('确定要删除吗？'))location.href='/reply/${reply.id!}/delete'">删除</a>
            </#if>
            <a href="javascript:replythis('${reply.user.username}');">回复</a>
          </span>
        </#if>
      </div>
      <#if reply.editor?? && reply.editor == 'markdown'>
        <p>${reply.marked(reply.content)}</p>
      <#elseif reply.editor?? && reply.editor  == 'wangeditor'>
        ${reply.content!}
      </#if>
    </div>
  </div>
    <#if reply_has_next>
      <div class="divide"></div>
    </#if>
  </#list>
</#macro>