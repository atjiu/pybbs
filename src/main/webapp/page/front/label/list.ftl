<#include "/page/front/common/_layout.ftl"/>
<@html title="标签 - ${siteTitle!}" description="" page_tab="label" sidebar_user_info="show"
sidebar_create="show" sidebar_jfbbs_run_status="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${baseUrl!}/">首页</a></li>
            <li class="active">标签</li>
        </ol>
    </div>
    <div class="panel-body">
        <#list labels as label>
            <a href="${baseUrl!}/label/${label.name!}" class="item_node">${label.name!}</a>
        </#list>
    </div>
</div>
</@html>