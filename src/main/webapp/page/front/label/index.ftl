<#include "/page/front/common/_layout.ftl"/>
<@html title="标签 - ${siteTitle!}" description="" page_tab="label" sidebar_user_info="show">
<div class="panel panel-default">
    <#if label??>
        <div class="panel-body" style="padding-bottom: 10px;">
            <div class="media">
                <#if label.img??>
                    <div class="media-left">
                        <img src="${label.img!}" class="big-avatar">
                    </div>
                </#if>
                <div class="media-body">
                    <b style="font-size: 16px;">${label.name!}</b>
                    <div class="pull-right small-gray">
                        话题总数 ${label.topic_count!}
                        <#--<#if session.user??>-->
                            <#--<a href="javascript:void(0)">加入收藏</a>-->
                        <#--</#if>-->
                    </div>
                    <#if label.description??>
                        <p class="small-gray mar-5">${label.description!}</p>
                    </#if>
                    <#if session.user??>
                        <p><a href="${baseUrl!}/topic/create/${label.name!}" class="btn btn-raised btn-sm btn-default">发布话题</a></p>
                    </#if>
                </div>
            </div>
        </div>
        <div class="divide" style="margin-top: 0;"></div>
        <div class="panel-body">
            <#include "/page/front/common/topic_list.ftl"/>
                <@topic_list topics=page.getList()/>
        </div>
        <#include "/page/front/common/_paginate.ftl" />
        <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/label/${label.name!}" urlParas="" />
    <#else>
        <div class="panel-body">
            标签未找到
        </div>
    </#if>
</div>
</@html>