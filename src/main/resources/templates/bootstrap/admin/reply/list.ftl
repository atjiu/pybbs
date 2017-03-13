<#include "../../common/layout.ftl"/>
<@html page_title="回复列表">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../components/admin_left.ftl">
    <@admin_left page_tab="reply"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a href="/">主页</a> / 回复列表
      </div>
      <div class="table-responsive">
        <#include "../../components/reply_list.ftl"/>
        <@user_replies replies=page.getContent()/>
      </div>
      <div class="panel-body" style="padding: 0 15px;">
        <#include "../../components/paginate.ftl"/>
        <@paginate currentPage=(page.getNumber() + 1) totalPage=page.getTotalPages() actionUrl="/admin/reply/list" urlParas="" showdivide="no"/>
      </div>
    </div>
  </div>
</div>
</@html>