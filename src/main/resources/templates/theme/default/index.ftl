<#include "layout/layout.ftl"/>
<@html page_title="首页" page_tab="index">
    <#if active?? && active>
        <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
            <strong>激活成功</strong>
        </div>
    </#if>
    <div class="row">
        <div class="col-md-9">
            <div class="card">
                <div class="card-header">
                    <ul class="nav nav-pills">
                        <li class="nav-item"><a class="nav-link <#if tab=="all">active</#if>" href="/?tab=all">全部</a></li>
                        <li class="nav-item"><a class="nav-link <#if tab=="good">active</#if>" href="/?tab=good">精华</a></li>
                        <li class="nav-item"><a class="nav-link <#if tab=="hot">active</#if>" href="/?tab=hot">最热</a></li>
                        <li class="nav-item"><a class="nav-link <#if tab=="newest">active</#if>" href="/?tab=newest">最新</a></li>
                        <li class="nav-item"><a class="nav-link <#if tab=="noanswer">active</#if>" href="/?tab=noanswer">无人问津</a></li>
                    </ul>
                </div>
                <div class="card-body">
                    <@tag_topics pageNo=pageNo!1 tab=tab!"all">
                        <#include "components/topics.ftl"/>
                        <@topics page=page/>

                        <#include "components/paginate.ftl"/>
                        <@paginate currentPage=page.current totalPage=page.pages actionUrl="/" urlParas="&tab=${tab!}"/>
                    </@tag_topics>
                </div>
            </div>
        </div>
        <div class="col-md-3 d-none d-md-block">
            <#if _user??>
                <#include "components/user_info.ftl"/>
            <#else>
                <#include "components/welcome.ftl"/>
            </#if>
            <#include "components/score.ftl"/>
            <@score limit=10/>
        </div>
    </div>
</@html>
