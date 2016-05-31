<#include "../common/layout.ftl"/>
<@html page_title="${currentUser.nickname!}回复的话题" page_tab="user">
    <div class="row">
        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-heading">
                    ${currentUser.nickname!}回复的话题
                </div>
                <#include "../components/userreplies.ftl"/>
                <@userreplies replies=page.getList()/>
                <div class="panel-body" style="padding: 0 15px;">
                    <#include "../components/paginate.ftl"/>
                    <@paginate currentPage=page.getPageNumber() totalPage=page.getTotalPage() actionUrl="/user/replies/${currentUser.nickname!}" urlParas="" showdivide="no"/>
                </div>
            </div>
        </div>
        <div class="col-md-3 hidden-sm hidden-xs"></div>
    </div>
</@html>