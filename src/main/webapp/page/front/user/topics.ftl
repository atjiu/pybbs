<#include "/page/front/common/_layout.ftl"/>
<@html title="${current_user.nickname!} 创建的话题 - ${siteTitle!}" description="${current_user.nickname!} 创建的话题" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">${current_user.nickname!} 创建的话题</li>
        </ol>
    </div>
    <div class="panel-body">
        <#include "/page/front/common/topic_list.ftl"/>
        <@topic_list topics=page.getList()/>
    </div>
    <#include "/page/front/common/_paginate.ftl" />
    <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/user/topics/${current_user.id!}" urlParas="" />
</div>
</@html>