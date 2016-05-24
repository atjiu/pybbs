<#include "/page/front/common/_layout.ftl"/>
<@html title="私信 - ${siteTitle!}" description="私信 - ${siteTitle!}" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">私信</li>
        </ol>
    </div>
    <div class="panel-body">
        <#list msgContacts as contact>
            <div class="media">
                <div class="media-left">
                    <a href="${baseUrl!}/user/${contact.to_author_id!}">
                        <img src="${contact.avatar!}" alt="avatar" class="media-object avatar">
                    </a>
                </div>
                <div class="media-body">
                    <div class="media-heading">
                        <p><a href="${baseUrl!}/user/${contact.to_author_id!}">${contact.nickname!}</a></p>
                        <p><a href="${baseUrl!}/message/read/${contact.id!}">${contact.last_msg_content!}...</a></p>
                    </div>
                    <p class="small-fade">
                        <span>${contact.formatDate(contact.last_msg_time)!}</span>
                        <a href="${baseUrl!}/message/read/${contact.id!}">共${contact.msg_count!}条对话</a>
                        &nbsp;•&nbsp;<a href="javascript:if(confirm('确定要删除这条会话吗？\r\n注意: 这会删除当前会话下的所有信息!!')) location.href='${baseUrl!}/message/delete/${contact.id!}'">删除</a>
                    </p>
                </div>
            </div>
            <#if contact_has_next>
                <div class="divide"></div>
            </#if>
        </#list>
    </div>
</div>
</@html>