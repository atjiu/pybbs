<#include "./common/layout.ftl">
<@html page_title="首页 - ${siteTitle!}">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <ul class="nav nav-pills">
                    <li <#if tab == 'all'>class="active"</#if>><a href="/?tab=all">全部</a></li>
                    <li <#if tab == 'good'>class="active"</#if>><a href="/?tab=good">精华</a></li>
                    <li <#if tab == 'noreply'>class="active"</#if>><a href="/?tab=noreply">等待回复</a></li>
                    <li class="dropdown <#if tab != 'good' && tab != 'all' && tab != 'noreply'>active</#if>"
                        style="margin-right: 8px;">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)" data-target="#">
                        ${sectionName!} <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <#list sections as section>
                                <li>
                                    <a href="/?tab=${section.tab!}">${section.name!}</a>
                                </li>
                            </#list>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="panel-body paginate-bot">
                <#include "./components/topics.ftl"/>
                <@topics/>
                <#include "./components/paginate.ftl"/>
                <@paginate currentPage=page.getPageNumber() totalPage=page.getTotalPage() actionUrl="/" urlParas="&tab=${tab!}"/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs">
        <#if userinfo??>
            <#include "./components/userinfo.ftl">
            <@info/>
            <#include "./components/createtopic.ftl">
            <@createtopic/>
        <#else>
            <#include "./components/welcome.ftl">
            <@welcome/>
        </#if>
        <#include "./components/qrcode.ftl"/>
        <@qrcode/>
        <#--<#include "./components/scores.ftl"/>-->
        <#--<@userscores limit=10 />-->
    </div>
</div>
</@html>