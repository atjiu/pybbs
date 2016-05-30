<#macro replies replies>
    <#list replies as reply>
    <div class="media" id="reply${reply.id}">
        <div class="media-left">
            <a href="/user/${reply.author}"><img src="${reply.getAvatarByNickname(reply.author)}" class="avatar" alt=""/></a>
        </div>
        <div class="media-body reply-content">
            <div class="media-heading gray">
                <a href="/user/${reply.author!}">${reply.author!} </a>
                ${reply.formatDate(reply.in_time)}
                <#if userinfo??>
                    <span class="pull-right">
                        <@py.hasPermission name="reply:edit" id="${userinfo.id!}">
                            <a href="/r/edit?id=${reply.id!}">编辑</a>
                        </@py.hasPermission>
                        <@py.hasPermission name="reply:delete" id="${userinfo.id!}">
                            <a href="javascript:if(confirm('确定要删除吗？'))location.href='/r/delete?id=${reply.id!}'">删除</a>
                        </@py.hasPermission>
                        <a href="javascript:replythis('${reply.author}');">回复</a>
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