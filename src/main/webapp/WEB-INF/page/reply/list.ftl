<#include "../common/layout.ftl"/>
<@html page_tab="system" page_title="回复列表">
<div class="row">
    <div class="col-md-9">
        <div class="panel panel-default">
            <div class="panel-heading">
                <a href="/">主页</a> / 回复列表
            </div>
            <div class="table-responsive">
                <#include "../components/replylist.ftl"/>
                <@userreplies replies=page.getList()/>
            </div>
            <div class="panel-body" style="padding: 0 15px;">
                <#include "../components/paginate.ftl"/>
                    <@paginate currentPage=page.getPageNumber() totalPage=page.getTotalPage() actionUrl="/r/list" urlParas="" showdivide="no"/>
            </div>
        </div>
    </div>
    <div class="col-md-3 hidden-sm hidden-xs"></div>
</div>
</@html>