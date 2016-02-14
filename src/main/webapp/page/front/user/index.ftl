<#include "/page/front/common/_layout.ftl"/>
<@html title="${current_user.nickname} 的个人主页 - ${siteTitle!}" description="${current_user.nickname} 的个人主页" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">${current_user.nickname!}</li>
        </ol>
    </div>
    <div class="panel-body">
        <img class="avatar" src="${current_user.avatar!}" title="${current_user.nickname!}">&nbsp;
        <span><a href="${baseUrl!}/user/${current_user.id!}">${current_user.nickname!}</a></span>
        <#if current_user.id != session.user.id>
            <span class="pull-right">
                <a href="javascript:void(0);" data-toggle="modal" data-target="#newMessageModal" class="btn btn-raised  btn-default">私信</a>
            </span>
        </#if>
        <div>
            <#if day??>
                已连续签到<a href="${baseUrl!}/mission/top10" style="font-size: 20px; color: red;"> ${day!} </a>天
            </#if>
        </div>
        <div style="margin-top: 10px;">
            <span>积分: ${current_user.score!} </span>
        </div>
        <div>
            <span>
                <span class="glyphicon glyphicon-bookmark"></span>
                <a class="dark" href="${baseUrl!}/user/collects/${current_user.id!}">
                    ${collectPage.totalRow!}话题收藏
                </a>
            </span>
        </div>
        <#if current_user.url?? && current_user.url != "">
            <div>
                <span class="glyphicon glyphicon-home"></span>
                <a href="${current_user.url!}" target="_blank">${current_user.url!}</a>
            </div>
        </#if>
        <span class="glyphicon glyphicon-log-in"></span>
        注册时间 ${current_user.formatDate(current_user.in_time)!}
    </div>
</div>

<div class="panel panel-default">
    <div class="panel-heading">
        <span class="glyphicon glyphicon-th-list"></span>
        最近创建的话题
    </div>
    <div class="panel-body">
        <#include "/page/front/common/topic_list.ftl"/>
        <@topic_list topics=topics.getList()/>
    </div>
    <div style="padding: 10px;">
        <a class="dark" href="${baseUrl!}/user/topics/${current_user.id!}">查看更多&gt;&gt;</a>
    </div>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <span class="glyphicon glyphicon-th-list"></span>
        最近参与的话题
    </div>
    <div class="panel-body">
        <#include "/page/front/common/topic_list.ftl"/>
        <@topic_list topics=myReplyTopics.getList()/>
    </div>
    <div style="padding: 10px;">
        <a class="dark" href="${baseUrl!}/user/replies/${current_user.id!}">查看更多&gt;&gt;</a>
    </div>
</div>
<#--私信弹出框-->
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" id="newMessageModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">新私信</h4>
            </div>
            <div class="modal-body">
                <form action="${baseUrl!}/message/save" method="post" id="newMessageForm">
                    <input type="hidden" name="toAuthorId" value="${current_user.id!}">
                    <div class="form-group">
                        <input type="text" class="form-control" disabled value="${current_user.nickname!}" placeholder="用户昵称">
                    </div>
                    <div class="form-group">
                        <textarea name="messageContent" id="messageContent" rows="6" class="form-control" placeholder="你想对TA说点什么..."></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-raised  btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-raised  btn-default" onclick="sendMessage()">发送</button>
            </div>
        </div>
    </div>
</div>
<script>
    function sendMessage() {
        var messageContent = $("#messageContent").val();
        if($.trim(messageContent) == "") {
            alert("私信内容不能为空");
        } else if(messageContent.length > 1000) {
            alert("私信内容不能超过1000个字符");
        } else {
            $("#newMessageForm").submit();
        }
    }
</script>
</@html>