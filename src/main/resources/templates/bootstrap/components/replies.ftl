<#macro reply replies>
  <#list replies as reply>
  <div class="media" id="reply${reply.id}">
    <div class="media-left">
      <a href="/user/${reply.user.username}"><img src="${reply.user.avatar}" class="avatar" alt=""/></a>
    </div>
    <div class="media-body reply-content">
      <div class="media-heading gray">
        <a href="/user/${reply.user.username!}">${reply.user.username!} </a>
      ${reply.formatDate(reply.inTime)}
        <#if user??>
          <span class="pull-right">
            <#if _roles?seq_contains("reply:edit")>
              <a href="/reply/${reply.id}/edit">编辑</a>
            </#if>
            <#if _roles?seq_contains("reply:delete")>
              <a href="javascript:if(confirm('确定要删除吗？'))location.href='/reply/${reply.id!}/delete'">删除</a>
            </#if>
            <a href="javascript:replythis('${reply.user.username}');">回复</a>
          </span>
        </#if>
      </div>
      <p>
      ${reply.marked(reply.content)}
      </p>
    </div>
  </div>
    <#if reply_has_next>
    <div class="divide mar-top-5"></div>
    </#if>
  </#list>
</#macro>