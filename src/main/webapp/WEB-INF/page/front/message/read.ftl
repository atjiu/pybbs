<#include "/page/front/common/_layout.ftl"/>
<@html title="私信对话 - ${siteTitle!}" description="私信对话 - ${siteTitle!}" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">与${toUser.nickname!}的私信对话</li>
            <a href="${baseUrl!}/message" class="pull-right">返回私信列表</a>
        </ol>
    </div>
    <div class="panel-body">
        <div class="media">
            <div class="media-body">
                <form action="${baseUrl!}/message/save" method="post" id="newMessageForm">
                    <input type="hidden" name="toAuthorId" value="${toUser.id!}">
                    <div class="form-group" style="padding-left: 100px;">
                        <textarea name="messageContent" id="messageContent" rows="4" class="form-control" placeholder="你想对TA说点什么..."></textarea>
                    </div>
                    <div class="form-group pull-right">
                        <input type="button" value="发送" id="sendMessage" data-loading-text="Loading..." class="btn btn-raised  btn-default">
                    </div>
                </form>
            </div>
            <div class="media-right">
                <a href="${baseUrl!}/user/${session.user.id!}">
                    <img src="${session.user.avatar}" alt="" class="avatar">
                </a>
            </div>
        </div>
        <div class="divide"></div>
        <#list messages as message>
            <div class="media">
                <#if message.author_id == session.user.id>
                    <div class="media-left">
                        <a href="${baseUrl!}/user/${session.user.id!}">
                            <img src="${message.avatar}" alt="" class="avatar">
                        </a>
                    </div>
                </#if>
                <div class="media-body">
                    <div class="alert <#if message.author_id == session.user.id>alert-info <#else> alert-success pull-right</#if>" role="alert" style="width: 80%;">
                        <p>
                            <#if message.author_id == session.user.id>
                                <a href="${baseUrl!}/user/${session.user.id!}">我</a>:
                            <#else>
                                <a href="${baseUrl!}/user/${session.user.id!}">${message.nickname!}</a>:
                            </#if>
                            ${message.content!}
                        </p>
                        <p class="small-fade">
                            ${message.formatDate(message.in_time)!}
                        </p>
                    </div>
                </div>
                <#if message.author_id != session.user.id>
                    <div class="media-right">
                        <a href="${baseUrl!}/user/${message.author_id!}">
                            <img src="${message.avatar}" alt="" class="avatar">
                        </a>
                    </div>
                </#if>
            </div>
        </#list>
    </div>
</div>
<script>
    $("#sendMessage").on("click", function() {
        var messageContent = $("#messageContent").val();
        if($.trim(messageContent) != ''){
            $(this).button('loading');
            $("#newMessageForm").submit();
        }
    })
</script>
</@html>