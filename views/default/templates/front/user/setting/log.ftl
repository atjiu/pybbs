<#include "../../layout/" + layoutName/>
<@html page_title="个人日志记录" page_tab="setting">
<div class="row">
  <div class="col-md-3 hidden-sm hidden-xs">
    <#include "../../layout/setting_menu.ftl"/>
    <@setting_menu setting_menu_tab="log"/>
  </div>
  <div class="col-md-9">
    <div class="panel panel-default">
      <div class="panel-heading">
        <a data-pjax href="/">主页</a> / 个人日志记录
      </div>
      <div class="table-responsive">
        <table class="table table-striped">
          <tr>
            <th>ID</th>
            <th>详情</th>
            <th>添加日期</th>
          </tr>
          <#list page.getContent() as log>
            <tr>
              <td>${log.id!}</td>
              <td>${log.eventDescription!}</td>
              <td>${model.formatDate(log.inTime)!}</td>
            </tr>
          </#list>
        </table>
      </div>
      <#include "../../components/paginate.ftl"/>
        <@paginate currentPage=page.number totalPage=page.totalPages actionUrl="/user/setting/log" urlParas=""/>
    </div>
  </div>
</div>
</@html>