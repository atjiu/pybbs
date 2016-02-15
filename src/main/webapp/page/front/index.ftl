<#include "/page/front/common/_layout.ftl"/>
<@html title="首页 - ${siteTitle!}" description="" page_tab="topic" sidebar_user_info="show" sidebar_checkin="show"
    sidebar_create="show" sidebar_scoretop="show" sidebar_not_reply_topic="show" sidebar_jfbbs_run_status="show">
    <div class="panel panel-default">
        <div class="panel-heading ot-tab-heading">
            <ul class="nav nav-pills">
                <li <#if l == 0 && tab == 'all'> class="active" </#if> style="margin-right: 8px;">
                    <a href="${baseUrl!}/?tab=all">最新</a>
                </li>
                <li <#if tab == 'good'> class="active" </#if> style="margin-right: 8px;">
                    <a href="${baseUrl!}/?tab=good">精华</a>
                </li>
                <li <#if tab == 'noreply'> class="active" </#if> style="margin-right: 8px;">
                    <a href="${baseUrl!}/?tab=noreply">等待回复</a>
                </li>
                <li class="dropdown <#if tab != 'good' && tab != 'all' && tab != 'noreply'>active</#if>"
                    style="margin-right: 8px;">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)" data-target="#">
                        ${sectionName!} <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <#list sections as section>
                            <li>
                                <a href="${baseUrl!}/?tab=${section.tab!}">${section.name!}</a>
                            </li>
                        </#list>
                    </ul>
                </li>
                <#if l?? && l &gt; 0>
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