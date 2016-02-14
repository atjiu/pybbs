<#include "/page/front/common/_layout.ftl"/>
<@html title="首页 - ${siteTitle!}" description="" page_tab="topic" sidebar_user_info="show" sidebar_checkin="show"
    sidebar_create="show" sidebar_scoretop="show" sidebar_not_reply_topic="show" sidebar_jfbbs_run_status="show">
    <div class="panel panel-default">
        <div class="panel-heading ot-tab-heading">
            <ul class="nav nav-pills">
                <li <#if tab == 'good'> class="active" </#if> style="margin-right: 8px;">
                    <a href="${baseUrl!}/?tab=good">精华</a>
                </li>
                <#list sections as section>
                    <li <#if tab == section.tab> class="active" </#if> style="margin-right:8px;">
                        <a href="${baseUrl!}/?tab=${section.tab!}&q=${q!}">${section.name!}</a>
                    </li>
                </#list>
                <#if l??>
                    <li class="active">
                        <a href="javascript:;">标签：${_label.name!}</a>
                    </li>
                <#elseif q?? && q != "">
                    <li class="active">
                        <a href="javascript:;">搜索：${q!}</a>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="panel-body">
            <#include "/page/front/common/topic_list.ftl"/>
            <@topic_list topics=page.getList()/>
        </div>
        <#include "/page/front/common/_paginate.ftl" />
        <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${baseUrl!}/" urlParas="&q=${q!}&tab=${tab!}" />
    </div>
</@html>